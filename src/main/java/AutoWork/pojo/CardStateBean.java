package AutoWork.pojo;

import java.util.List;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 10:57 2020/12/11
 */
public class CardStateBean {

    /**
     * code : 200
     * msg : success
     * data : [{"type":"todaySign","state":0,"typeName":"今天签到","shortName":"签到"},{"type":"weekly","state":1,"typeName":"本周周报","shortName":"周报"}]
     * flag : 0
     */

    private int code;
    private String msg;
    private int flag;
    private List<DataBean> data;

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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : todaySign
         * state : 0
         * typeName : 今天签到
         * shortName : 签到
         */

        private String type;
        private int state;
        private String typeName;
        private String shortName;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }
}
