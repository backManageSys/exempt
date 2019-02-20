package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.publicdatas.order.OrderState;

import java.util.Date;
import java.util.List;

public class OrderItem {
    private int id;
    private double total;
    private Date date;
    private String address;
    private String phone;
    private String comment;
    private List<OrderFoodItem> foods;
    private OrderState orderState;

    public OrderItem() {
    }

    public OrderItem(int id, double total, Date date, String address, String phone, String comment, List<OrderFoodItem> foods, OrderState orderState) {
        this.id = id;
        this.total = total;
        this.date = date;
        this.address = address;
        this.phone = phone;
        this.comment = comment;
        this.foods = foods;
        this.orderState = orderState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<OrderFoodItem> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderFoodItem> foods) {
        this.foods = foods;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
