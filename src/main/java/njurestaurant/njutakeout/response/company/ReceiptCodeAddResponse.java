package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

public class ReceiptCodeAddResponse extends Response {
    private int receiptCodeId;

    public ReceiptCodeAddResponse(int receiptCodeId) {
        this.receiptCodeId = receiptCodeId;
    }

    public int getReceiptCodeId() {
        return receiptCodeId;
    }

    public void setReceiptCodeId(int receiptCodeId) {
        this.receiptCodeId = receiptCodeId;
    }
}
