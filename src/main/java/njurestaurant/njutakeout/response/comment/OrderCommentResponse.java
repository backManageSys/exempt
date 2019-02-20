package njurestaurant.njutakeout.response.comment;

import njurestaurant.njutakeout.response.Response;

public class OrderCommentResponse extends Response {
    private String info;

    public OrderCommentResponse() {
        this.info = "success";
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
