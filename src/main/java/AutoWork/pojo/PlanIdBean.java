package AutoWork.pojo;

import java.util.List;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 09:31 2020/11/29
 */
public class PlanIdBean {

    /**
     * code : 200
     * msg : success
     * data : [{"totalCount":0,"pageSize":10,"totalPage":0,"currPage":1,"isDeleted":null,"createBy":null,"modifiedBy":null,"createTime":null,"modifiedTime":null,"createByName":null,"modifiedByName":null,"orderBy":"create_time","sort":"desc","planId":"93e04987dff02379bd211ae92b722460","schoolId":null,"depId":null,"depName":null,"planName":"2018级移动互联应用技术（移动应用开发）顶岗实习","planNumber":null,"planLevel":null,"planGrades":null,"type":"D_PRACTICE","startTime":"2020-11-23 00:00:00","endTime":"2021-06-11 00:00:00","subsidy":null,"description":null,"planState":null,"backup":null,"isSign":1,"isAuto":"0","batchId":null,"practiceStus":null,"snowFlakeId":null,"batchName":"2020-2021","planPaper":{"isDeleted":0,"createTime":"2020-11-18 22:28:03","planPaperId":"95887e74fb28a2676aa7b7137a2aecec","planId":"93e04987dff02379bd211ae92b722460","dayPaperNum":null,"weekPaperNum":200,"monthPaperNum":null,"summaryPaperNum":3000,"weekReportCount":29,"paperReportCount":null,"monthReportCount":null,"summaryReportCount":1,"maxDayNum":8000,"maxWeekNum":8000,"maxMonthNum":8000,"maxSummaryNum":8000,"snowFlakeId":1000339,"dayPaper":false,"weekPaper":true,"monthPaper":false,"summaryPaper":false},"planPaperMap":null,"attachments":null,"planMajors":null,"planClasses":null,"planAppraiseItem":null,"planAppraiseItemDtos":null,"planAppraiseItemEntities":null,"majorNames":null,"createName":"王法强","attachmentNum":1,"planIds":null,"signCount":null,"auditState":null,"majorTeacher":null,"majorId":null,"majorName":null,"majorField":null,"semester":null,"planExtra":null,"majorTeacherId":null,"isSysDefault":0,"teacherName":"黄珍","isCopyAllocate":null,"isCopy":null,"isShowUpDel":null,"isBuyInsurance":null,"stuItemIds":null,"selfMultiple":null,"schoolTeacher":null,"companyMultiple":null,"multipleTheory":null}]
     */

    private int code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * planId : 93e04987dff02379bd211ae92b722460
         */
        private String planId;

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }
    }
}
