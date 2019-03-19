package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class VerifySignException extends Exception{
    private WrongResponse response = new WrongResponse(10121, "验证签名错误。");

    public WrongResponse getResponse() {
        return response;
    }

}
