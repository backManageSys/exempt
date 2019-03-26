package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class AliUserErrorException extends Exception{
    private WrongResponse response = new WrongResponse(10010, "用户信息错误");

    public WrongResponse getResponse() {
        return response;
    }
}
