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
    private CodeType codeType;
    private String time;
    private String sign;
    /*选择收款的二维码*/
    private String type;

    public GetQrCodeParameters(String ip, String id, String money, String memo, String merchantName, CodeType codeType,String time, String sign, String type) {
        this.ip = ip;
        this.id = id;
        this.money = money;
        this.memo = memo;
        this.merchantName = merchantName;
        this.codeType = codeType;
        this.time = time;
        this.sign = sign;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }
}
