package njurestaurant.njutakeout.exception;


import njurestaurant.njutakeout.response.WrongResponse;

public class SystemException extends Exception {
    private WrongResponse response = new WrongResponse(10001, "System is error.");

    public WrongResponse getResponse() {
        return response;
    }
}
