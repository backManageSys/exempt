package njurestaurant.njutakeout.parameters.order;

import java.util.List;

public class OrderCommentParameters {
    private int orderId;
    private List<Integer> foodIds;
    private String comment;

    public OrderCommentParameters() {
    }

    public OrderCommentParameters(int orderId, List<Integer> foodIds, String comment) {
        this.orderId = orderId;
        this.foodIds = foodIds;
        this.comment = comment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Integer> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<Integer> foodIds) {
        this.foodIds = foodIds;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
