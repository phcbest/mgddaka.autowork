package AutoWork.service.impl;

import AutoWork.dao.DataDao;
import AutoWork.db.ManagerPojo;
import AutoWork.db.UserAllWeekWorkPojo;
import AutoWork.db.UserInfoPojo;
import AutoWork.pojo.LoginInfoBean;
import AutoWork.pojo.response.ResponseResult;
import AutoWork.service.IUserService;
import AutoWork.utils.Constant;
import AutoWork.utils.IdWorker;
import AutoWork.utils.JwtUtils;
import AutoWork.utils.OkHttpUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 13:45 2020/12/22
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    private final OkHttpUtils okHttpUtils;
    private final DataDao dataDao;
    private final IdWorker idWorker;

    @Autowired
    public UserServiceImpl(OkHttpUtils okHttpUtils, DataDao dataDao, IdWorker idWorker) {
        this.okHttpUtils = okHttpUtils;
        this.dataDao = dataDao;
        this.idWorker = idWorker;
    }

    @Override
    public String addUser(UserInfoPojo userInfoPojo) {
//        return "已经停止了录入功能，本程序仅用于学习使用";
        try {
            log.info(userInfoPojo.toString());
            userInfoPojo.setAddres(userInfoPojo.getAddres().trim());
            if (userInfoPojo.getAddres().split("\\|").length != 3) {
                return "地址输入错误";
            }
            Response execute = okHttpUtils.post("https://api.moguding.net:9000/session/user/v1/login", null,
                    new LoginInfoBean(userInfoPojo.getPwd(), userInfoPojo.getPhone())).execute();
            String json = execute.body().string();
            if (!execute.isSuccessful()) {
                OkHttpUtils.getUsableProxy();
                return "请求失败,代理出现问题,请稍后请求";
            }
            execute.close();
            String substring = json.substring(json.indexOf(":") + 1, json.indexOf(","));
            log.info("注册后的验证状态" + substring);
            if (substring.equals("200")) {
                dataDao.addUser(userInfoPojo);
                return "验证成功，已经录入服务器";
            } else {
                return "失败,账号密码错误或没有此账号";
            }
        } catch (Exception e) {
            return "数据库录入失败，有可能是已经录入了";
        }
    }

    @Override
    public String deleteUser(UserInfoPojo userInfoPojo) throws IOException {
        UserInfoPojo userByPhone = dataDao.getUserByPhone(userInfoPojo.getPhone());
        if (userByPhone == null) {
            return "没注册过该用户";
        }
        Response execute = okHttpUtils.post("https://api.moguding.net:9000/session/user/v1/login", null,
                new LoginInfoBean(userInfoPojo.getPwd(), userInfoPojo.getPhone())).execute();
        String json = execute.body().string();
        if (!execute.isSuccessful()) {
            OkHttpUtils.getUsableProxy();
            return "请求失败,代理出现问题,请稍后请求";
        }
        execute.close();
        String substring = json.substring(json.indexOf(":") + 1, json.indexOf(","));
        log.info(substring);
        if (substring.equals("200")) {
            try {
                log.info("删除用用户，已提交到删除记录库");
                dataDao.addUserDeleteHistory(userByPhone);
                dataDao.deleteUser(userInfoPojo);
            } catch (Exception e) {
                e.printStackTrace();
                return "已经删除过了";
            }
            return "权限确定成功，已删除";
        }
        return "失败,可能是账号获密码错误";
    }

    @Override
    public ResponseResult userLogin(UserInfoPojo userInfoPojo,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        //记录当前请求ip
        String remoteAddr = request.getRemoteAddr();
        dataDao.addRemoteAddr(new Date(), remoteAddr);
        //查找用户是否正确
        UserInfoPojo user = dataDao.getUserByPhone(userInfoPojo.getPhone());
        if (user == null) {
            return ResponseResult.FAILURE("失败,不存在用该用户");
        }
        if (!user.getPwd().equals(userInfoPojo.getPwd())) {
            return ResponseResult.FAILURE("失败,密码错误,请使用录入时的账号密码");
        }
        //生成一个token返回给用户
        HashMap<String, Object> userTokenMap = new HashMap<>();
        userTokenMap.put("phone", user.getPhone());
        userTokenMap.put("remote_address", remoteAddr);
        userTokenMap.put("random_code", new Random().nextInt());
        String token = JwtUtils.createToken(userTokenMap);
        //在服务器中更新当前的token
        if (dataDao.getTokenByPhone(user.getPhone()) == null) {
            dataDao.addToken(token, new Date(), user.getPhone());
        } else {
            dataDao.setToken(token, new Date(), user.getPhone());
        }
        return ResponseResult.SUCCESS("登录成功").setData(token);
    }

    /**
     * 查找当前使用的打卡数据
     *
     * @param request
     * @param response
     */
    @Override
    public ResponseResult userPunchClockInfo(HttpServletRequest request, HttpServletResponse response) {
        String phone = verifyIdentity(request);
        String substring = phone.substring(0, 1);
        if (substring.equals("0")) {
            return ResponseResult.FAILURE(phone.substring(1));
        }
        phone = phone.substring(1);
        //查找用户
        UserInfoPojo userByPhone = dataDao.getUserByPhone(phone);
        if (userByPhone == null) {
            return ResponseResult.FAILURE("失败,没有该用户");
        }
        userByPhone.setPwd("******");
        return ResponseResult.SUCCESS("请求成功").setData(userByPhone);
    }

    /**
     * 设置打卡信息
     *
     * @param userInfoPojo
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResponseResult setUserPunchClockInfo(UserInfoPojo userInfoPojo, HttpServletRequest request, HttpServletResponse response) {
        String phone = verifyIdentity(request);
        String substring = phone.substring(0, 1);
        if (substring.equals("0")) {
            log.info(phone.substring(1));
            return ResponseResult.FAILURE(phone.substring(1));
        }

        //修改当前数据
        if (
                userInfoPojo.getStart_time().trim().isEmpty() ||
                        userInfoPojo.getAddres().trim().isEmpty() ||
                        userInfoPojo.getMail_addre().trim().isEmpty() ||
                        userInfoPojo.getInfo().trim().isEmpty()
        ) {
            return ResponseResult.FAILURE("必须参数为:周记开始时时间&打卡地址&邮箱地址&打卡信息");
        }
        if (!userInfoPojo.getMail_addre().trim().matches("^([A-Za-z0-9_\\-.\\u4e00-\\u9fa5])+@([A-Za-z0-9_\\-.])+\\.([A-Za-z]{2,8})$")) {
            return ResponseResult.FAILURE("请输入正常的邮箱");
        }
        if (userInfoPojo.getAddres().trim().split("\\|").length != 3) {
            return ResponseResult.FAILURE("地址格式错误，请认真输入，这很重要");
        }

        userInfoPojo.setPhone(phone.substring(1));
        dataDao.setUser(userInfoPojo);
        return ResponseResult.SUCCESS("修改成功");
    }

    /**
     * 用户自定义周记
     *
     * @param request
     * @param response
     * @param weekWork
     * @return
     */
    @Override
    public ResponseResult userAddWeekWork(HttpServletRequest request, HttpServletResponse response, String weekWork) {
        String phone = verifyIdentity(request);
        String substring = phone.substring(0, 1);
        if (substring.equals("0")) {
            log.info(phone.substring(1));
            return ResponseResult.FAILURE(phone.substring(1));
        }
        phone = phone.substring(1);
        if (weekWork.isEmpty()) {
            return ResponseResult.FAILURE("周记不能为空");
        }
        //使用雪花算法生成id，给周记一个id
        dataDao.addWeekWorkByUser(weekWork, phone, idWorker.nextId());
        return ResponseResult.SUCCESS("添加成功");
    }

    @Override
    public ResponseResult userDeleteWeekWork(HttpServletRequest request, HttpServletResponse response, String weekWorkId) {
        String phone = verifyIdentity(request);
        String substring = phone.substring(0, 1);
        if (substring.equals("0")) {
            log.info(phone.substring(1));
            return ResponseResult.FAILURE(phone.substring(1));
        }
        if (weekWorkId.isEmpty()) {
            return ResponseResult.FAILURE("周记id不能为空");
        }
        //删除周记对象
        phone = phone.substring(1);
        dataDao.deleteUserWeekWork(phone, weekWorkId);
        return ResponseResult.SUCCESS("删除成功");
    }


    @Override
    public ResponseResult userWeekWork(HttpServletRequest request, HttpServletResponse response) {
        String phone = verifyIdentity(request);
        String substring = phone.substring(0, 1);
        if (substring.equals("0")) {
            log.info(phone.substring(1));
            return ResponseResult.FAILURE(phone.substring(1));
        }
        phone = phone.substring(1);
        //查找所有的对应周记
        List<UserAllWeekWorkPojo> userAllWeekWorkByPhone = dataDao.getUserAllWeekWorkByPhone(phone);
        if (userAllWeekWorkByPhone == null || userAllWeekWorkByPhone.size() == 0) {
            return ResponseResult.FAILURE("没有自定义过周记");
        }
        return ResponseResult.SUCCESS("成功").setData(userAllWeekWorkByPhone);
    }

    @Override
    public ResponseResult getVersionInfo(String version) {
        List<ManagerPojo> managerInfo = dataDao.getManagerInfo();

        HashMap<String, Object> versionInfo = new HashMap<>();
        for (ManagerPojo managerPojo : managerInfo) {
            if ("version".equals(managerPojo.getName())) {
                versionInfo.put("newVersion", managerPojo.getInfo());
            }
            if ("version_link".equals(managerPojo.getName())) {
                versionInfo.put("link", managerPojo.getInfo());
            }
        }
        return ResponseResult.SUCCESS("成功").setData(versionInfo);
    }

    @Override
    public ResponseResult setVersionInfo(HashMap<String, String> info) {
        List<ManagerPojo> managerInfo = dataDao.getManagerInfo();
        String safeToken = "";
        for (ManagerPojo managerPojo : managerInfo) {
            if ("admin_token".equals(managerPojo.getName())) {
                safeToken = managerPojo.getInfo();
                break;
            }
        }
        if (!info.get("token").equals(safeToken)) {
            return ResponseResult.FAILURE("token错误");
        }
        dataDao.setManagerInfo("version", info.get("version"));
        dataDao.setManagerInfo("version_link", info.get("version_link"));
        return ResponseResult.SUCCESS("版本修改成功");
    }

    @Override
    public ResponseResult selectHistory(HttpServletResponse response, HttpServletRequest request, String phone, String number) {
        List<Map<String, String>> userHistory = dataDao.selectHistoryByUserPhone(phone, number);
        log.info(userHistory.toString());
        return ResponseResult.SUCCESS("请求成功").setData(userHistory);
    }

    @Override
    public ResponseResult addQqCanal(String token, String qq, String email) {
        //向qmsg添加一个qq号
//
//        okHttpUtils.postUseAddFormDataPart("https://qmsg.zendee.cn/createQmsgUserQQ",
//                );
        return ResponseResult.SUCCESS("待开发");
    }

    @Override
    public ResponseResult removeQqCanal(String token, String qq) {
        return ResponseResult.SUCCESS("待开发");
    }


    private String verifyIdentity(HttpServletRequest request) {
        String token = request.getHeader(Constant.HTTP_HEADER_AUTHORIZATION);
        Claims claims = null;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return "0token错误,可能已经过期";
        }
        if (claims == null) {
            return "0token解析为空";
        }
        //从token中提取出手机号
        String phone = (String) claims.get("phone");
        //查找是否有对应的token
        String tokenByPhone = dataDao.getTokenByPhone(phone);
        if (phone == null) {
            return "0解析错误";
        }
        if (!token.equals(tokenByPhone)) {
            return "0失败,token已经失效";
        }
        UserInfoPojo userByPhone = dataDao.getUserByPhone(phone);
        if (userByPhone == null) {
            return "0失败,没有该用户";
        }
        return "1" + phone;
    }
}
