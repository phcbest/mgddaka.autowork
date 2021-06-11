package AutoWork.db;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 15:47 2020/12/25
 */
public class UserAllWeekWorkPojo {
    public String week_work;
    public String phone;
    public String week_id;

    public UserAllWeekWorkPojo(String week_work, String phone, String week_id) {
        this.week_work = week_work;
        this.phone = phone;
        this.week_id = week_id;
    }

    public String getWeek_work() {
        return week_work;
    }

    public void setWeek_work(String week_work) {
        this.week_work = week_work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeek_id() {
        return week_id;
    }

    public void setWeek_id(String week_id) {
        this.week_id = week_id;
    }
}
