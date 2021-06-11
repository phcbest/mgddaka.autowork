package AutoWork.controller.user;

import AutoWork.db.UserInfoPojo;
import AutoWork.pojo.response.ResponseResult;
import AutoWork.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:33 2020/11/29
 */
@Slf4j
@RestController
@CrossOrigin//允许跨域
public class UserController {

    private final IUserService userService;


    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public ResponseResult userLogin(@RequestBody UserInfoPojo userInfoPojo,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        return userService.userLogin(userInfoPojo, request, response);
    }

    @GetMapping("/getVersionInfo")
    public ResponseResult getVersionInfo(@RequestParam String version) {
        return userService.getVersionInfo(version);
    }

    @PostMapping("/admin/setVersionInfo")
    public ResponseResult setVersionInfo(@RequestBody HashMap<String, String> info) {
        return userService.setVersionInfo(info);
    }


    @PostMapping("/user/punch_clock_info")
    public ResponseResult getPunchClockInfo(HttpServletRequest request,
                                            HttpServletResponse response) {
        return userService.userPunchClockInfo(request, response);
    }

    @PostMapping("/user/add_week_work")
    public ResponseResult addWeekWorkByUser(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam String weekWork) {
        return userService.userAddWeekWork(request, response, weekWork);
    }

    @PostMapping("/user/delete_week_work")
    public ResponseResult deleteWeekWorkByUser(HttpServletRequest request,
                                               HttpServletResponse response,
                                               @RequestParam String weekWorkId) {
        return userService.userDeleteWeekWork(request, response, weekWorkId);
    }

    @PostMapping("/user/get_week_work")
    public ResponseResult getWeekWorkByUser(HttpServletRequest request,
                                            HttpServletResponse response) {
        return userService.userWeekWork(request, response);
    }


    //查询用户用户的打卡记录
    @GetMapping("/user/history/{phone}/{number}")
    public ResponseResult selectHistory(HttpServletResponse response, HttpServletRequest request,
                                        @PathVariable String phone, @PathVariable String number) {
        return userService.selectHistory(response, request, phone, number);
    }

    @PostMapping("/user/set_punch_clock_info")
    public ResponseResult setPunchClockInfo(@RequestBody UserInfoPojo userInfoPojo,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        return userService.setUserPunchClockInfo(userInfoPojo, request, response);
    }

    @PostMapping("/user/add")
    public String addUser(@RequestBody UserInfoPojo userInfoPojo) throws IOException {
        return userService.addUser(userInfoPojo);
    }


    @DeleteMapping("/user/delete")
    public String deleteUser(@RequestBody UserInfoPojo userInfoPojo) throws IOException {
        return userService.deleteUser(userInfoPojo);
    }


    //todo 需要开发Qmsg的增加与删除接口

    @PostMapping("/user/addQqCanal/{token}/{qq}/{email}")
    public ResponseResult addQqCanal(@PathVariable String token, @PathVariable String qq, @PathVariable String email) throws IOException {
        return userService.addQqCanal(token, qq, email);
    }


    @PostMapping("/user/removeQqCanal/{token}/{qq}")
    public ResponseResult removeQqCanal(@PathVariable String token, @PathVariable String qq) throws IOException {
        return userService.removeQqCanal(token, qq);
    }

}
