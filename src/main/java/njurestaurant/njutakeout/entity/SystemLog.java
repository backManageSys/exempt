package njurestaurant.njutakeout.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:12
 * @Version: 1.0
 */
@Entity
@Table(name = "SYSTEMLOG")
public class SystemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;

    @Column(name = "requestip")
    private String requestip; //操作IP

    @Column(name = "type")
    private String type ;//  操作类型 1 操作记录 2异常记录

    @Column(name = "userid")
    private int userid ;// 操作人ID

    @Column(name = "description")
    private String description;// 操作描述

    @Column(name = "actiondate")
    private Date actiondate ;// 操作时间

    @Column(name = "exceptioncode")
    private int exceptioncode ;// 异常code

    @Column(name = "exceptiondetail")
    private String exceptiondetail ;// 异常详情

    @Column(name = "actionmethod")
    private String actionmethod ;//请求方法

    @Column(name = "params")
    private String params;//请求参数

    public String getRequestip() {
        return requestip;
    }

    public void setRequestip(String requestip) {
        this.requestip = requestip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActiondate() {
        return actiondate;
    }

    public void setActiondate(Date actiondate) {
        this.actiondate = actiondate;
    }

    public int getExceptioncode() {
        return exceptioncode;
    }

    public void setExceptioncode(int exceptioncode) {
        this.exceptioncode = exceptioncode;
    }

    public String getExceptiondetail() {
        return exceptiondetail;
    }

    public void setExceptiondetail(String exceptiondetail) {
        this.exceptiondetail = exceptiondetail;
    }

    public String getActionmethod() {
        return actionmethod;
    }

    public void setActionmethod(String actionmethod) {
        this.actionmethod = actionmethod;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
