package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class OrderWrongInputException extends Exception {
    private WrongResponse response ;
    public OrderWrongInputException(WrongResponse response) {
        this.response = response;
    }
    public WrongResponse getResponse() {
        return response;
    }
}
