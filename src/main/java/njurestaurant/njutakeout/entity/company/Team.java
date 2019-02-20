package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    @Column(name = "supervisor")
    private String supervisor;
    @Column(name = "area")
    private String area;
    @Column(name = "status")
    private String status;
    @Column(name = "verifyCode")
    private String verifyCode;
    @Column(name = "operator")
    private String operator;
    @Column(name = "addTime")
    private Date addTime;
    @Column(name = "teamName")
    private String teamName;

    public Team(String supervisor, String area, String status, String verifyCode, String operator, Date addTime, String teamName) {
        this.supervisor = supervisor;
        this.area = area;
        this.status = status;
        this.verifyCode = verifyCode;
        this.operator = operator;
        this.addTime = addTime;
        this.teamName = teamName;
    }

    public Team() {
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVertfyCode() {
        return verifyCode;
    }

    public void setVertifyCode(String vetifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
