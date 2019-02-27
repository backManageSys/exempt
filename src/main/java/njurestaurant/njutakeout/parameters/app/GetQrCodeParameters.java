package njurestaurant.njutakeout.parameters.app;

import njurestaurant.njutakeout.publicdatas.app.CodeType;

/**
 * web端请求二维码接口
 */
public class GetQrCodeParameters {
//    {IP:"ip地址",id:"充值方编号",money:"待付款金额",备注:"我们平台的唯一标识符",商户名:"merchantId",time="10位的时间戳",sign="签名算法(http://kfb.im/index/doc/sign.do)"}
    /*ip address*/
    private String ip;
    /*充值方编号*/
    private String id;
    /*待付款金额*/
    private String money;
    private String memo;
    private String merchantName;
    private String time;
    private String sign;
    /*选择收款类型*/
    private int payTypeId;

    public GetQrCodeParameters(String ip, String id, String money, String memo, String merchantName,  String time, String sign, int payTypeId) {
        this.ip = ip;
        this.id = id;
        this.money = money;
        this.memo = memo;
        this.merchantName = merchantName;
        this.time = time;
        this.sign = sign;
        this.payTypeId = payTypeId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }
}
