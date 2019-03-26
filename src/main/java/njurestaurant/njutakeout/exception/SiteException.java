package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class SiteException extends Exception{
    private String message="";
    WrongResponse response = new WrongResponse(10121,message);
    public SiteException(String message) {
        this.message=message;
    }

    public WrongResponse getResponse() {
        return response;
    }
}
