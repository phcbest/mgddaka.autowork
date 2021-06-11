package AutoWork.auto;

import AutoWork.dao.DataDao;
import AutoWork.db.QQListpojo;
import AutoWork.db.UserInfoPojo;
import AutoWork.db.WeekNotesPojo;
import AutoWork.pojo.*;
import AutoWork.utils.EmailSender;
import AutoWork.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 12:08 2020/11/28
 */
@Slf4j
@Component
public class PunchClock {

    private final OkHttpUtils okHttpUtils;

    private final DataDao dataDao;
    private String am_or_pm_string;

    static HashMap<String, String> emailList = new HashMap<String, String>();


    @Autowired
    public PunchClock(OkHttpUtils okHttpUtils, DataDao dataDao) throws InterruptedException, IOException {
        this.okHttpUtils = okHttpUtils;
        this.dataDao = dataDao;
    }

    public void upWork() throws Exception {
        doPunchTheClock("START", "\n上班打卡失败", "\n上班打卡成功");
    }

    public void downWork() throws Exception {
        doPunchTheClock("END", "\n下班打卡失败", "\n下班打卡成功");
    }

    public void makeWeek() throws Exception {
        final List<WeekNotesPojo> weekNotes = dataDao.getWeekNotes();
        //
        List<UserInfoPojo> users = dataDao.getAllUser();

        //下方需要使用的qq渠道用户
        getQQUser();

        // 测试使用
//        users.removeIf(user -> !user.getPhone().equals("18600007886")&&!user.getPhone().equals(""));
//        for (UserInfoPojo userInfoPojo : users) {
//            log.info(userInfoPojo.toString());
//        }
//        if (true) {
//            return;
//        }
        //
        for (final UserInfoPojo userInfoPojo : users) {
            Thread.sleep(5000);
            //判断是否需要写周记
            if (userInfoPojo.getMin_week_word() == 0) {
                log.info("无需自动写周记" + userInfoPojo.getPhone());
                continue;
            }
            //时间和周数计算
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date yyyyMmDd = simpleDateFormat.parse(userInfoPojo.getStart_time());
            final long week = ((System.currentTimeMillis() - yyyyMmDd.getTime()) / 604800000) + 1;
            int oWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
            final long dayOfWeek = oWeek == -1 ? 86400000 * 6 : 86400000 * oWeek;
            //计算符合条件的周记
            String notes = null;
            for (int i = 0; i < 20; i++) {
                notes = weekNotes.get(new Random().nextInt(weekNotes.size() - 1)).getNotes();
                if (notes.length() > userInfoPojo.getMin_week_word()) {
                    break;
                } else {
                    notes = null;
                }
            }
            if (notes == null) {
                sendInfo("数据库无符合周记，请自行撰写", userInfoPojo.getMail_addre());
                continue;
            }
            //
            LoginInfoBean loginRequest = new LoginInfoBean(userInfoPojo.getPwd(), userInfoPojo.getPhone());
            userInfoPojo.setPwd("******");
            String finalNotes = notes;
            okHttpUtils.post("https://api.moguding.net:9000/session/user/v1/login", null,
                    loginRequest,
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            log.info(userInfoPojo.getPhone() + "周记失败，登录失败");
                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "周记失败，登录失败", userInfoPojo.getMail_addre());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            LoginRespondBean loginRespond = new Gson().fromJson(response.body().string(), LoginRespondBean.class);
                            response.close();
                            Map<String, String> header = new HashMap<>();
                            header.put("Authorization", loginRespond.getData().getToken());

                            okHttpUtils.post("https://api.moguding.net:9000/practice/plan/v1/getPlanByStu",
                                    header, null, new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            log.info(userInfoPojo.getPhone() + "周记失败，得到playId失败");
                                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "周记失败，得到playId失败", userInfoPojo.getMail_addre());
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            String string = response.body().string();
                                            response.close();
                                            PlanIdBean planIdBean = new Gson().fromJson(string, PlanIdBean.class);
                                            WeekNotesBean weekNotesBean = new WeekNotesBean(
                                                    "week",
                                                    "",
                                                    "第" + week + "周",
                                                    "0.0",
                                                    planIdBean.getData().get(0).getPlanId(),
                                                    simpleDateFormat.format(System.currentTimeMillis() - dayOfWeek) + " 00:00:00",
                                                    "",
                                                    simpleDateFormat.format(System.currentTimeMillis() - dayOfWeek + 6 * 86400099) + " 23:59:59",
                                                    "第" + week + "周",
                                                    finalNotes,
                                                    "0.0"
                                            );
                                            okHttpUtils.post("https://api.moguding.net:9000/practice/paper/v1/save", header, weekNotesBean,
                                                    new Callback() {
                                                        @Override
                                                        public void onFailure(Call call, IOException e) {
                                                            log.info(userInfoPojo.getPhone() + "周记失败，提交失败");
                                                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "周记撰写失败", userInfoPojo.getMail_addre());
                                                        }

                                                        @Override
                                                        public void onResponse(Call call, Response response) throws IOException {
                                                            String request = response.body().string();
                                                            response.close();
                                                            log.info(userInfoPojo.getPhone() + "周记返回" + request);
                                                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "\n周记撰写结果\n" + request, userInfoPojo.getMail_addre());
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }
    }

    private void getQQUser() {
        emailList.clear();
        for (QQListpojo qqListpojo : dataDao.getAllQQbyEmail()) {
            emailList.put(qqListpojo.getEmail(), qqListpojo.getQq());
        }
    }


    private void doPunchTheClock(final String state, final String f, final String s) throws Exception {
        //代理测试
        OkHttpUtils.getUsableProxy();
        //留出冗余时间来进行代理测试
        Thread.sleep(1000 * 60);
        //用户筛选
        List<UserInfoPojo> userList = dataDao.getAllUser();
        am_or_pm_string = Calendar.AM == Calendar.getInstance().get(Calendar.AM_PM) ? "ad" : "pm";

        //下方需要使用的已打卡用户
        ArrayList<String> successHistoryByDate = dataDao.findSuccessHistoryByDate
                (new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date()), am_or_pm_string);

        //下方需要使用的qq渠道用户
        getQQUser();

        userList.removeIf(userInfoPojo -> successHistoryByDate.contains(userInfoPojo.getPhone()));
//        //todo 测试使用
//        userList.removeIf(user -> !user.getPhone().equals("")&&!user.getPhone().equals("18479715128"));
//        for (UserInfoPojo userInfoPojo : userList) {
//            log.info(userInfoPojo.toString());
//        }
//        if (true) {
//            return;
//        }
        log.info("需要打卡的人数" + userList.size());
        int progress = 1;

        for (final UserInfoPojo userInfoPojo : userList) {
            Thread.sleep(1000);
            log.info(">>>>>>>>>>>>>>>>>>>>当前进度" + progress++ + "<<<<<<<<<<<<<<<<<<<<");
            pcLogin(state, f, s, userInfoPojo);
        }
    }

    private void pcLogin(String state, String f, String s, UserInfoPojo userInfoPojo) throws IOException {
        LoginInfoBean loginRequest = new LoginInfoBean(userInfoPojo.getPwd(), userInfoPojo.getPhone());
        userInfoPojo.setPwd("******");
        okHttpUtils.post("https://api.moguding.net:9000/session/user/v1/login", null, loginRequest,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        {
                            log.info(userInfoPojo.getPhone() + state + "失败，登录失败");
                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "失败，登录失败", userInfoPojo.getMail_addre());
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        LoginRespondBean loginRespond = null;
                        String bodyString = response.body().string();
                        response.close();
                        try {
                            loginRespond = new Gson().fromJson(bodyString, LoginRespondBean.class);
                        } catch (JsonSyntaxException e) {
                            log.info("将数据转为实体类失效，目标数据" + bodyString);
                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "将数据转为实体类失效，目标数据" + bodyString, userInfoPojo.getMail_addre());
                            return;
                        }
                        if (loginRespond.getData() == null || loginRespond.getData().getToken() == null) {
                            log.info("空的token" + bodyString);
                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "获得的token为null" + bodyString, userInfoPojo.getMail_addre());
                            return;
                        }
                        pcPlanId(loginRespond, userInfoPojo, state, f, s);
                    }
                });
    }

    private void pcPlanId(LoginRespondBean loginRespond, UserInfoPojo userInfoPojo, String state, String f, String s) throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", loginRespond.getData().getToken());
        okHttpUtils.post("https://api.moguding.net:9000/practice/plan/v1/getPlanByStu", header, null,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        log.info(userInfoPojo.getPhone() + state + "失败，获得playId失败");
                        sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "失败，获得playId失败", userInfoPojo.getMail_addre());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        PlanIdBean planIdBean = null;
                        String stringPlanId = response.body().string();
                        response.close();
                        try {
                            planIdBean = new Gson().fromJson(stringPlanId, PlanIdBean.class);
                        } catch (JsonSyntaxException e) {
                            log.info("PlanId将数据转为实体类失效，目标数据" + stringPlanId);
                            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + "PlanId将数据转为实体类失效，目标数据" + stringPlanId, userInfoPojo.getMail_addre());
                            return;
                        }
                        pcPlanCard(planIdBean, userInfoPojo, state, header, f, s);
                    }
                });
    }

    private void pcPlanCard(PlanIdBean planIdBean, UserInfoPojo userInfoPojo, String state, Map<String, String> header, String f, String s) throws IOException {

        String planId = null;
        try {
            planId = planIdBean.getData().get(0).getPlanId();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(userInfoPojo.getPhone() + state + "失败，得到planId为null");
            sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + f + state + "\n失败，得到planId失败", userInfoPojo.getMail_addre());
            return;
        }
        WorkStateBean workStateBean = new WorkStateBean(
                "中国",
                "中国" + userInfoPojo.getAddres().replace("|", ""),
                userInfoPojo.getAddres().split("\\|")[0],
                userInfoPojo.getAddres().split("\\|")[1],
                "",
                userInfoPojo.getInfo(),
                planId,
                state,
                "Android",
                ""
        );
        okHttpUtils.post("https://api.moguding.net:9000/attendence/clock/v1/save", header, workStateBean,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        log.info(userInfoPojo.getPhone() + state + "失败，提交失败");
                        sendInfo("尾号" + userInfoPojo.getPhone().substring(6) + f + state + "失败，提交失败", userInfoPojo.getMail_addre());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        log.info(userInfoPojo.getPhone() + state + "成功");
                        dataDao.addSuccessHistoryByDate(userInfoPojo.getPhone(),
                                am_or_pm_string,
                                new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date()));
                        response.close();
                        sendInfo(s + "\n" +
                                "用户:" + "尾号" + userInfoPojo.getPhone().substring(6) + "\n" +
                                "打卡地址:" + userInfoPojo.getAddres() + "\n" +
                                "打卡信息:" + userInfoPojo.getInfo() + "\n", userInfoPojo.getMail_addre());
                    }
                });
    }


    public void sendInfo(String info, String address) {
//        getQQUser();
        String qq = emailList.get(address);
        if (qq != null && !qq.isEmpty()) {
            //发送qq消息
            Call post = null;
            //url中文部分转码
            try {
                info = URLEncoder.encode(info, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.info("qq发送错误,转码url错误：" + qq);
                return;
            }
            String url = "https://qmsg.zendee.cn/send/43fe6c4c307b37892be797a4b47d656e" + "?msg=" + info + "&qq=" + qq;
            Callback cb = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    log.info("qq发送错误,网络请求出错" + qq);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String sendResult = response.body().string();
                    //请求成功可能会返回稀奇古怪的东西，需要确保解析正确
                    HashMap<String, Object> sendResultInfo = null;
                    try {
                        sendResultInfo = new Gson().
                                fromJson(sendResult, new TypeToken<HashMap<String, Object>>() {
                                }.getType());
                    } catch (JsonSyntaxException e) {
                        log.info("qq发送失败,请求qmsg返回数据无法解析" + qq);
                        return;
                    }
                    if ((Boolean) sendResultInfo.get("success")) {
                        log.info("qq发送成功" + qq);
                    } else {
                        log.info("qq发送错误,请求结果出错" + qq);
                    }
                    response.close();
                }
            };
            try {
                okHttpUtils.post(url, null, null, cb);
            } catch (IOException e) {
                log.info("qq发送错误,网络请求call对象异常：" + qq);
                return;
            }

        } else {
            //发送邮件消息
            try {
                EmailSender.sendInfo(info, address);
            } catch (Exception exception) {
                log.info("邮件发送错误" + address);
            }
        }
    }

}
