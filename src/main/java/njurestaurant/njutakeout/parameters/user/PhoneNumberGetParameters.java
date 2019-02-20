package njurestaurant.njutakeout.parameters.user;

public class PhoneNumberGetParameters {
    private String encryptData;
    private String sessionKey;
    private String ivCode;

    public PhoneNumberGetParameters() {
    }

    public PhoneNumberGetParameters(String encryptData, String sessionKey, String ivCode) {
        this.encryptData = encryptData;
        this.sessionKey = sessionKey;
        this.ivCode = ivCode;
    }

    public String getEncryptData() {
        return encryptData;
    }

    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getIvCode() {
        return ivCode;
    }

    public void setIvCode(String ivCode) {
        this.ivCode = ivCode;
    }
}
