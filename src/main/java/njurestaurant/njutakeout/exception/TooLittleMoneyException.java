package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class TooLittleMoneyException extends Exception {
    private WrongResponse response = new WrongResponse(88888, "Too little money");

    public WrongResponse getResponse() {
        return response;
    }
}
