package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class PersonalCardDoesNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10411, "Personal Card does not exists.");

    public WrongResponse getResponse() {
        return response;
    }
}
