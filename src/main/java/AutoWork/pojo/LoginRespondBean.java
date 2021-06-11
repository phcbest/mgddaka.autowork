package AutoWork.pojo;

import java.util.List;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:12 2020/11/28
 */
public class LoginRespondBean {


    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtb2d1ZGluZy11c2VyIiwic3ViIjoie1wibG9naW5UeXBlXCI6XCJhbmRyb2lkXCIsXCJ1c2VySWRcIjoxMDIwOTkyMTR9IiwiYXVkIjoibW9ndWRpbmciLCJleHAiOjE5MjIxNjE0NTgsIm5iZiI6MTYwNjU0MTM1OCwiaWF0IjoxNjA2NTQyMjU4fQ.TM0B8nZUlP2cNub324lfAwqwRvb_jZmEGHqkhPiouM_W3KqdicHpTxua-MO7nROKWqGFOeiC2zQW2he4s3qUpw
         */


        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
