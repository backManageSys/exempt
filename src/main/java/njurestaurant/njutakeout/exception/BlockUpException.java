package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class BlockUpException extends Exception {
    private WrongResponse response = new WrongResponse(1010, "该账户已停用。");

    public WrongResponse getResponse() {
        return response;
    }
}
