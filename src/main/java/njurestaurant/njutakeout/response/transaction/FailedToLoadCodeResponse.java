package njurestaurant.njutakeout.response.transaction;

import njurestaurant.njutakeout.response.Response;

/**
 * 失败，则服务器返回：
 * {status:"failed",reason:"失败原因（目前有网络异常、未获取到供码设备）"}
 */
public class FailedToLoadCodeResponse extends Response {
    private String status;
    private String reason;

    public FailedToLoadCodeResponse(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
