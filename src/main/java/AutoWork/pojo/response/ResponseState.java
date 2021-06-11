package AutoWork.pojo.response;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:01 2020/12/22
 */
public enum ResponseState {
    /**
     * 返回状态
     */
    SUCCESS(200, "操作成功", true),
    FAILURE(500, "请求失败", false);

    private int code;
    private String message;
    private boolean success;

    ResponseState(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
