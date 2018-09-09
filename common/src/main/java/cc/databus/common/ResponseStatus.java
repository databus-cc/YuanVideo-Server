package cc.databus.common;

public enum ResponseStatus {

    OK(200, "OK"),
    NotFound(404, "Not found"),
    BadRequest(400, "Bad request");

    private int status;
    private String errMsg;

    ResponseStatus(int status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
    }

    public int status() {
        return this.status;
    }

    public String errMsg() {
        return this.errMsg;
    }
}
