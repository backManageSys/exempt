package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class OrderNotPayedException extends Exception {
    private WrongResponse response = new WrongResponse(1015, "订单未支付，不可获取二维码。");

    public WrongResponse getResponse() {
        return response;
    }
}
