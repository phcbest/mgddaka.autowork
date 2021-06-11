package AutoWork.pojo;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 22:45 2020/11/28
 */
public class WeekNotesBean {

    /**
     * reportType : week
     * address :
     * weeks : 第2周
     * latitude : 0.0
     * planId : 717ac14fc89826a3e3f7a401fdef502e
     * startTime : 2020-11-23 00:00:00
     * yearmonth :
     * endTime : 2020-11-29 23:59:59
     * title : 第二周
     * content : 俗话说的好一年之季在于春，一天之季在于晨，又是一个星期的开始，早上起来呼吸着窗
     * 外的新鲜空气，来到厂里开始新的工作，将上个星期的零件图把他装配起来，我以为本来是很
     * 简单的事，不过事实并不是想象中的那样的简单，在装配过程中出现了许多问题，这下可把我
     * 个弄荤了，都不知道该从何下手，比如说在装配的过程中出现尺寸的不一样，出现很大的间隙
     * 等等其他的许多问题。想象中的那样的简单，在装配过程中出现了许
     * longitude : 0.0
     */

    private String reportType;
    private String address;
    private String weeks;
    private String latitude;
    private String planId;
    private String startTime;
    private String yearmonth;
    private String endTime;
    private String title;
    private String content;
    private String longitude;

    public WeekNotesBean(String reportType, String address, String weeks, String latitude, String planId, String startTime, String yearmonth, String endTime, String title, String content, String longitude) {
        this.reportType = reportType;
        this.address = address;
        this.weeks = weeks;
        this.latitude = latitude;
        this.planId = planId;
        this.startTime = startTime;
        this.yearmonth = yearmonth;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
        this.longitude = longitude;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
