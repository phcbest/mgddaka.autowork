package AutoWork.dao;

import AutoWork.db.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:13 2020/11/28
 */
@Repository
public interface DataDao {


    List<UserInfoPojo> getAllUser();

    List<WeekNotesPojo> getWeekNotes();

    int addUser(UserInfoPojo userInfoPojo);

    int setUser(UserInfoPojo userInfoPojo);

    int deleteUser(UserInfoPojo userInfoPojo);

    UserInfoPojo getUserByPhone(String phone);

    int addUserDeleteHistory(UserInfoPojo userInfoPojo);

    int addSuccessHistoryByDate(@Param("text") String text, @Param("am_or_pm") String am_or_pm, @Param("date") String date);

    ArrayList<String> findSuccessHistoryByDate(@Param("date") String date, @Param("am_or_pm") String am_or_pm);

    int addRemoteAddr(Date date, String addr);

    /**
     * 查找用户token
     */
    String getTokenByPhone(String phone);

    /**
     * 添加用户token
     */
    int addToken(String token, Date date, String phone);

    /**
     * 修改用户token
     */
    int setToken(String token, Date date, String phone);


    /**
     * 用户自定义周记
     *
     * @param text
     * @param phone
     * @return
     */
    int addWeekWorkByUser(String text, String phone, long week_id);

    /**
     * 删除用户自定义周记
     *
     * @param phone
     * @param weekWorkId
     */
    int deleteUserWeekWork(String phone, String weekWorkId);

    /**
     * 获得用户所有的周记
     *
     * @param phone
     * @return
     */
    List<UserAllWeekWorkPojo> getUserAllWeekWorkByPhone(String phone);

    /**
     * 获得版本信息
     */
    List<ManagerPojo> getManagerInfo();

    /**
     * 修改版本信息
     */
    int setManagerInfo(String name, String info);

    List<QQListpojo> getAllQQbyEmail();


    List<Map<String, String>> selectHistoryByUserPhone(@Param("phone") String phone,
                                                       @Param("number") String number);
}
