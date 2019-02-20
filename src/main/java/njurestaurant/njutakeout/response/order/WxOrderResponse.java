package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.util.PayCommonUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.SortedMap;
import java.util.TreeMap;

public class WxOrderResponse extends Response {
    @Value(value = "${wechat.sign_type}")
    private String SIGN_TYPE;
    @Value(value = "${wechat.api_key}")
    private String API_KEY;

    private String timeStamp;
    private String nonceStr;
    private String pakcage;
    private String signType;
    private String paySign;

    public WxOrderResponse() {
    }

    public WxOrderResponse(String timeStamp, String nonceStr, String pakcage) {
        this.timeStamp = timeStamp;
        this.nonceStr = nonceStr;
        this.pakcage = pakcage;
        this.signType = SIGN_TYPE;
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp", timeStamp + "");
        sortedMap.put("nonceStr", nonceStr);
        sortedMap.put("pakcage", pakcage);
        sortedMap.put("signType", SIGN_TYPE);
        this.paySign = PayCommonUtil.createSign("UTF-8", sortedMap, API_KEY);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPakcage() {
        return pakcage;
    }

    public void setPakcage(String pakcage) {
        this.pakcage = pakcage;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
