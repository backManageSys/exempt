package njurestaurant.njutakeout.parameters.order;

import java.util.Date;
import java.util.List;

public class OrderSubmitParameters {
    private double total;
    private Date date;
    private String address;
    private String comment;
    private List<OrderSubmitFood> foods;
    private String phone;

    public OrderSubmitParameters() {
    }


    public OrderSubmitParameters(double total, Date date, String address, String comment, List<OrderSubmitFood> foods, String phone) {
        this.total = total;
        this.date = date;
        this.address = address;
        this.comment = comment;
        this.foods = foods;
        this.phone = phone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<OrderSubmitFood> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderSubmitFood> foods) {
        this.foods = foods;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
