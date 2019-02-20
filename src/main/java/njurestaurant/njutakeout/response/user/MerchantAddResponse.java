package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class MerchantAddResponse extends Response {
    private int merchantId;

    public MerchantAddResponse(int merchantId) {
        this.merchantId = merchantId;
    }

    public MerchantAddResponse() {
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }
}
