package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "staffName")
    private String staffName;
    @Column(name = "team")
    private String team;
    @Column(name = "addTime")
    private Date addTime;
    @Column(name = "verifyCode")
    private String verifyCode;
    @Column(name = "operator")
    private String operator;
    @Column(name = "status")
    private String status;
    @Column(name = "post")
    private String post;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Staff() {
    }

    public Staff(String staffName, String team, Date addTime, String verifyCode, String operator, String status, String post, User user) {
        this.staffName = staffName;
        this.team = team;
        this.addTime = addTime;
        this.verifyCode = verifyCode;
        this.operator = operator;
        this.status = status;
        this.post = post;
        this.user = user;
    }

    public Staff(String staffName, String team, Date addTime, String verifyCode, String operator, String status, String post) {
        this.staffName = staffName;
        this.team = team;
        this.addTime = addTime;
        this.verifyCode = verifyCode;
        this.operator = operator;
        this.status = status;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
