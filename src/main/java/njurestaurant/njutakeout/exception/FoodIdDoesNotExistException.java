package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class FoodIdDoesNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10002, "Food id does not exists.");

    public WrongResponse getResponse() {
        return response;
    }
}
