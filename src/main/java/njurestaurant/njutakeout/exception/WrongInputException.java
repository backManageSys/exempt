package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class WrongInputException extends Exception {
    private WrongResponse response = new WrongResponse(10410, "输入错误.");

    public WrongResponse getResponse() {
        return response;
    }
}
