package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class BlankInputException extends Exception {
    private WrongResponse response = new WrongResponse(10120, "The input is blank.");

    public WrongResponse getResponse() {
        return response;
    }
}
