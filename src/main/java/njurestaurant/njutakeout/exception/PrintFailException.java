package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class PrintFailException extends Exception {
    private WrongResponse response = new WrongResponse(10020, "Print fails.");

    public WrongResponse getResponse() {
        return response;
    }
}
