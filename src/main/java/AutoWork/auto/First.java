package AutoWork.auto;

import AutoWork.dao.DataDao;
import AutoWork.pojo.ProxyListBean;
import AutoWork.utils.EmailSender;
import AutoWork.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 20:49 2020/11/28
 */
@Slf4j
@Component
public class First {

    @Autowired
    PunchClock punchClock;

    final long oneDay = 24 * 60 * 60 * 1000;
    final long oneHour = 60 * 60 * 1000;


    public void exe() throws IOException {
//        todo 测试使用
//        try {
//            punchClock.upWork();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        //开启代理，跑一次初始数据
        OkHttpUtils.getUsableProxy();

        /**
         * 上班
         */
        newTask(true, executorService, "7:00:00", "开始打上班卡", "上班打卡出错，请检查日志");
        //补打1
        newTask(true, executorService, "7:30:00", "开始补打上班卡", "上班补卡1出错，请检查日志");
        //补打2
        newTask(true, executorService, "8:00:00", "开始补打上班卡", "上班补卡2出错，请检查日志");
        /**
         * 下班
         */
        newTask(false, executorService, "17:00:00", "开始打下班卡", "下班打卡出错，请检查日志");
        //补打1
        newTask(false, executorService, "17:30:00", "开始补打下班卡", "下班补卡1出错，请检查日志");
        //补打2
        newTask(false, executorService, "18:00:00", "开始补打下班卡", "下班补卡2出错，请检查日志");


        /**
         * 周记
         */
        long week = getTimeMillis("10:00:00") - System.currentTimeMillis();
        week = week > 0 ? week : oneDay + week;
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    try {
                        log.info("开始写周记");
                        punchClock.makeWeek();
                    } catch (Exception e) {
                        try {
                            // TODO: 2021/6/11  填写管理员邮箱
                            EmailSender.sendInfo("周记撰写出错，请检查日志\n" + e.toString(), "");
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            }
        }, week, oneDay, TimeUnit.MILLISECONDS);
    }


    private void newTask(boolean isUp,
                         ScheduledExecutorService executorService,
                         String time,
                         String carState,
                         String emailSendStateInfo) {
        long clock = getTimeMillis(time) - System.currentTimeMillis();
        clock = clock > 0 ? clock : oneDay + clock;
        log.info("任务进栈" + isUp + "--------" + time + "--------" + clock);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(carState);
                    if (isUp) {
                        punchClock.upWork();
                    } else {
                        punchClock.downWork();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        // TODO: 2021/6/11 使用管理员邮箱接受服务器信息
                        EmailSender.sendInfo(emailSendStateInfo + e.toString(), "");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

            }
        }, clock, oneDay, TimeUnit.MILLISECONDS);
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
