package cc.databus.common;

/**
 * Biz response, composed of:
 * 1. status: int status
 * 2. errMsg: description of status
 * 3. data : response data
 */
public class JsonResponse {
    /**
     * Biz response code
     */
    private Integer status;

    /**
     * Biz response message
     */
    private String errMsg;

    /**
     * Biz response data
     */
    private Object data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static JsonResponse ok() {
        return new JsonResponse(ResponseStatus.OK.status(), ResponseStatus.OK.errMsg());
    }

    public static JsonResponse ok(Object data) {
        return new JsonResponse(ResponseStatus.OK.status(), ResponseStatus.OK.errMsg(), data);
    }

    public static JsonResponse badRequest(String errMsg) {
        return new JsonResponse(ResponseStatus.BadRequest.status(), errMsg);
    }

    public static JsonResponse badRequest(Object data) {
        return new JsonResponse(ResponseStatus.BadRequest.status(), ResponseStatus.BadRequest.errMsg(), data);
    }


    public JsonResponse(Integer status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
    }

    public JsonResponse(Integer status, String errMsg, Object data) {
        this.status = status;
        this.errMsg = errMsg;
        this.data = data;
    }
}
