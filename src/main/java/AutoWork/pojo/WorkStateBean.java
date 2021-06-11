package AutoWork.pojo;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 22:09 2020/11/28
 */
public class WorkStateBean {

    /**
     * country : 中国
     * address : 中国江西省九江市濂溪区莲花镇
     * province : 江西省
     * city : 九江市
     * latitude : 29.672233
     * description :
     * planId : 717ac14fc89826a3e3f7a401fdef502e
     * type : START
     * device : Android
     * longitude : 116.016498
     */

    private String country;
    private String address;
    private String province;
    private String city;
    private String latitude;
    private String description;
    private String planId;
    private String type;
    private String device;
    private String longitude;

    public WorkStateBean(String country, String address, String province, String city, String latitude, String description, String planId, String type, String device, String longitude) {
        this.country = country;
        this.address = address;
        this.province = province;
        this.city = city;
        this.latitude = latitude;
        this.description = description;
        this.planId = planId;
        this.type = type;
        this.device = device;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
