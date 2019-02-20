package njurestaurant.njutakeout.entity.order;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    private int orderId;
    @Column(name = "foodIds")
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> foodIds;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date")
    private Date date;

    public Comment() {
    }

    public Comment(int orderId, List<Integer> foodIds, String comment, Date date) {
        this.orderId = orderId;
        this.foodIds = foodIds;
        this.comment = comment;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
