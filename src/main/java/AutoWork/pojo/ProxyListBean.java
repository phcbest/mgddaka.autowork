package AutoWork.pojo;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 13:26 2021/3/3
 */
public class ProxyListBean {

    /**
     * check_count : 1
     * fail_count : 0
     * last_status : 1
     * last_time : 2021-03-03 05:10:39
     * proxy : 125.46.0.62:53281
     * region :
     * source :
     * type :
     */

    private int check_count;
    private int fail_count;
    private int last_status;
    private String last_time;
    private String proxy;
    private String region;
    private String source;
    private String type;

    public int getCheck_count() {
        return check_count;
    }

    public void setCheck_count(int check_count) {
        this.check_count = check_count;
    }

    public int getFail_count() {
        return fail_count;
    }

    public void setFail_count(int fail_count) {
        this.fail_count = fail_count;
    }

    public int getLast_status() {
        return last_status;
    }

    public void setLast_status(int last_status) {
        this.last_status = last_status;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProxyListBean(int check_count, int fail_count, int last_status, String last_time, String proxy, String region, String source, String type) {
        this.check_count = check_count;
        this.fail_count = fail_count;
        this.last_status = last_status;
        this.last_time = last_time;
        this.proxy = proxy;
        this.region = region;
        this.source = source;
        this.type = type;
    }

    public ProxyListBean() {
    }

    @Override
    public String toString() {
        return "ProxyListBean{" +
                "check_count=" + check_count +
                ", fail_count=" + fail_count +
                ", last_status=" + last_status +
                ", last_time='" + last_time + '\'' +
                ", proxy='" + proxy + '\'' +
                ", region='" + region + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
