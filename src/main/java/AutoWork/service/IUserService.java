package AutoWork.service;

import AutoWork.db.UserInfoPojo;
import AutoWork.pojo.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 13:44 2020/12/22
 */
public interface IUserService {
    /**
     * 添加用户的操作
     *
     * @param userInfoPojo
     * @return 返回结果
     */
    String addUser(UserInfoPojo userInfoPojo);

    /**
     * 删除用户的操作
     *
     * @param userInfoPojo
     * @return 返回结果
     */
    String deleteUser(UserInfoPojo userInfoPojo) throws IOException;

    /**
     * 用户登录
     *
     * @param userInfoPojo 只需要账号与密码
     * @param request
     * @param response
     * @return
     */
    ResponseResult userLogin(UserInfoPojo userInfoPojo,
                             HttpServletRequest request,
                             HttpServletResponse response);

    /**
     * 得到用户当的打卡信息
     *
     * @param request
     * @param response
     * @return
     */
    ResponseResult userPunchClockInfo(HttpServletRequest request, HttpServletResponse response);

    /**
     * 修改用户的打卡信息
     *
     * @param userInfoPojo
     * @param request
     * @param response
     * @return
     */
    ResponseResult setUserPunchClockInfo(UserInfoPojo userInfoPojo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户添加自定义周记
     *
     * @param request
     * @param response
     * @param weekWork
     * @return
     */
    ResponseResult userAddWeekWork(HttpServletRequest request, HttpServletResponse response, String weekWork);


    /**
     * 用户删除自定义周记
     *
     * @param request
     * @param response
     * @param weekWorkId
     * @return
     */
    ResponseResult userDeleteWeekWork(HttpServletRequest request, HttpServletResponse response, String weekWorkId);

    /**
     * 得到用户的所有周记
     *
     * @param request
     * @param response
     * @return
     */
    ResponseResult userWeekWork(HttpServletRequest request, HttpServletResponse response);

    ResponseResult getVersionInfo(String version);

    ResponseResult addQqCanal(String token, String qq, String email);

    ResponseResult removeQqCanal(String token, String qq);

    ResponseResult setVersionInfo(HashMap<String, String> info);

    ResponseResult selectHistory(HttpServletResponse response, HttpServletRequest request, String phone, String number);
}
