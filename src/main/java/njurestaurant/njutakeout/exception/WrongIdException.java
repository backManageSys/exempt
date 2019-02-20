package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class WrongIdException extends Exception {
    private WrongResponse response = new WrongResponse(10160, "id错误");

    public WrongResponse getResponse() {
        return response;
    }
}
