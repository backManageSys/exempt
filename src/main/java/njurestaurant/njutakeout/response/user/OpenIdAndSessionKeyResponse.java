package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class OpenIdAndSessionKeyResponse extends Response {
    private String openId;
    private String sessionKey;

    public OpenIdAndSessionKeyResponse() {
    }

    public OpenIdAndSessionKeyResponse(String openId, String sessionKey) {
        this.openId = openId;
        this.sessionKey = sessionKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
