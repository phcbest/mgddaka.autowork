package AutoWork;

import AutoWork.auto.First;
import AutoWork.dao.DataDao;
import AutoWork.utils.EmailSender;
import AutoWork.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 10:12 2020/11/28
 */

@Slf4j
@SpringBootApplication
@MapperScan("AutoWork.dao")
public class ApplicationAutoWork {

    private static First first;

    @Autowired
    public ApplicationAutoWork(First first) {
        ApplicationAutoWork.first = first;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationAutoWork.class, args);
        // TODO: 2020/11/29 生产环境才需要这一句
//        EmailSender.sendInfo("服务器启动", "");
        first.exe();
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0, 0);
    }

}


