package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class WaitingException extends Exception {
    private WrongResponse response = new WrongResponse(1011 ,"该账户正等待管理员审批。");

    public WrongResponse getResponse() {
        return response;
    }
}
