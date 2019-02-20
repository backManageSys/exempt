package njurestaurant.njutakeout.parameters.order;

public class WxOrderParameters {
    private String appId;
    private String mchId;
    private String nonceStr;
    private String sign;
    private String body;
    private String outTradeNo;
    private int totalFee;
    private String spbillCreateIp;
    private String notify_url;
    private String tradeTypel;
    private String openid;

    public WxOrderParameters(String appId, String mchId, String nonceStr, String outTradeNo, int totalFee, String spbillCreateIp, String notify_url, String tradeTypel, String openid) {
        this.appId = appId;
        this.mchId = mchId;
        this.nonceStr = nonceStr;
        this.body = "冉阔餐饮-九餐厅";
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.spbillCreateIp = spbillCreateIp;
        this.notify_url = notify_url;
        this.tradeTypel = tradeTypel;
        this.openid = openid;
        this.sign = "x********************************4";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTradeTypel() {
        return tradeTypel;
    }

    public void setTradeTypel(String tradeTypel) {
        this.tradeTypel = tradeTypel;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
