package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class AlipayNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(1001, "支付宝账号不存在。");

    public WrongResponse getResponse() {
        return response;
    }
}
