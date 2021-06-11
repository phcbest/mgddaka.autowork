package AutoWork.db;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 17:09 2021/1/25
 */
public class QQListpojo {
    String qq;
    String email;

    public QQListpojo(String qq, String email) {
        this.qq = qq;
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
