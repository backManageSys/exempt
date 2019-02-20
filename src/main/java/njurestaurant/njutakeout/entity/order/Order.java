package njurestaurant.njutakeout.entity.order;

import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.publicdatas.order.OrderState;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "foodOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "orderUUID")
    private String orderUUID;

    @Column(name = "orderState")
    private OrderState orderState;

    @Column(name = "total")
    private double total;

    @Column(name = "date")
    private Date date;

    @Column(name = "address")
    private String address;

    @Column(name = "comment")
    private String comment;

    @Column(name = "foods")
    @ElementCollection(targetClass = OrderFood.class)
    private List<OrderFood> foods;

    @Column(name = "phone")
    private String phone;

    @Column(name = "timeStamp")
    private String timeStamp;

    @Column(name = "nonceStr")
    private String nonceStr;

    @Column(name = "pakcage")
    private String pakcage;

    @Column(name = "signType")
    private String signType;

    @Column(name = "paySign")
    private String paySign;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(String orderUUID, OrderState orderState, double total, Date date, String address, String comment, List<OrderFood> foods, String phone, String timeStamp, String nonceStr, String pakcage, String signType, String paySign, User user) {
        this.orderUUID = orderUUID;
        this.orderState = orderState;
        this.total = total;
        this.date = date;
        this.address = address;
        this.comment = comment;
        this.foods = foods;
        this.phone = phone;
        this.timeStamp = timeStamp;
        this.nonceStr = nonceStr;
        this.pakcage = pakcage;
        this.signType = signType;
        this.paySign = paySign;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<OrderFood> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderFood> foods) {
        this.foods = foods;
    }

    public String getOrderUUID() {
        return orderUUID;
    }

    public void setOrderUUID(String orderUUID) {
        this.orderUUID = orderUUID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPakcage() {
        return pakcage;
    }

    public void setPakcage(String pakcage) {
        this.pakcage = pakcage;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
