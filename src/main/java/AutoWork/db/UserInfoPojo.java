package AutoWork.db;

public class UserInfoPojo {

    private String phone;
    private String pwd;
    private String mail_addre;
    private String addres;
    private String info;
    private int min_week_word;
    private String start_time;

    @Override
    public String toString() {
        return "UserInfoPojo{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", mail_addre='" + mail_addre + '\'' +
                ", addres='" + addres + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMail_addre() {
        return mail_addre;
    }

    public void setMail_addre(String mail_addre) {
        this.mail_addre = mail_addre;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getMin_week_word() {
        return min_week_word;
    }

    public void setMin_week_word(int min_week_word) {
        this.min_week_word = min_week_word;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
