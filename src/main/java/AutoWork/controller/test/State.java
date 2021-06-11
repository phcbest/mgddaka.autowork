package AutoWork.controller.test;

import AutoWork.dao.DataDao;
import AutoWork.pojo.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 00:20 2020/11/29
 */
@RestController
public class State {

    @Autowired
    private DataDao dataDao;

    @CrossOrigin//允许跨域
    @GetMapping("/state/is_open")
    public ResponseResult isOpen() {
        HashMap<String, Integer> msg = new HashMap<>();
        msg.put("user_size", dataDao.getAllUser().size());
        return ResponseResult.SUCCESS("成功").setData(msg);
    }
}
