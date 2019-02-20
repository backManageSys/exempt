package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class CompanyCardDoesNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10412, "Company Card does not exists.");

    public WrongResponse getResponse() {
        return response;
    }
}
