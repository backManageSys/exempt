package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class PortDoesNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10005, "Port does not exists.");

    public WrongResponse getResponse() {
        return response;
    }
}