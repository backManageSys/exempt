package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class WaitingforAuthorizeException extends Exception {
    private WrongResponse response = new WrongResponse(10014, "新设备，请等待审批");

    public WrongResponse getResponse() {
        return response;
    }
}
