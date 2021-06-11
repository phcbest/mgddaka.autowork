package AutoWork.pojo;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 13:48 2020/11/28
 */
public class LoginInfoBean {
    String password;
    String phone;
    String loginType = "android";
    String uuid = "";

    public LoginInfoBean(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
