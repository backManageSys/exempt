package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class PayTypeStopUsingException extends Exception {
    private WrongResponse response = new WrongResponse(156, "该通道已停用");

    public WrongResponse getResponse() {
        return response;
    }
}
