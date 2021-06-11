package AutoWork.db;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 20:16 2021/1/26
 */
public class ManagerPojo {
    String name;
    String info;

    public ManagerPojo(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ManagerPojo{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
