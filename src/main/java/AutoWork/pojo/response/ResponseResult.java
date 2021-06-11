package AutoWork.pojo.response;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:01 2020/12/22
 */
public class ResponseResult {

    public boolean success;
    public int code;
    public String message;
    public Object data;

    public ResponseResult(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ResponseResult(ResponseState state) {
        this.success = state.isSuccess();
        this.code = state.getCode();
        this.message = state.getMessage();
    }

    public static ResponseResult SUCCESS(String msg) {
        ResponseResult responseResult = new ResponseResult(ResponseState.SUCCESS);
        responseResult.setMessage(msg);
        return responseResult;
    }

    public static ResponseResult FAILURE(String msg) {
        ResponseResult responseResult = new ResponseResult(ResponseState.FAILURE);
        responseResult.setMessage(msg);
        return responseResult;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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

    public Object getData() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
}
