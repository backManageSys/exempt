package njurestaurant.njutakeout.response.transaction;

import njurestaurant.njutakeout.response.Response;

/*
成功，则服务器返回：
{url:"我们平台的网址(在二维码未失效的情况下可重定向到支付宝付款地址)重定向的接口",status:"success",orderid:"订单号"}
 */
public class GetQrCodeResponse extends Response {
    private String url;
    private String status;
    private String orderId;

    public GetQrCodeResponse(String url, String status, String orderId) {
        this.url = url;
        this.status = status;
        this.orderId = orderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
