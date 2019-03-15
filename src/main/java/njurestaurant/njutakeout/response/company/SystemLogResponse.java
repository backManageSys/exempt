package njurestaurant.njutakeout.response.company;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class SystemLogResponse {

    private int id ;

    private String requestip; //操作IP

    private String type ;//  操作类型 1 操作记录 2异常记录

    private String username ;// 操作人ID

    private String description;// 操作描述

    private Date actiondate ;// 操作时间

    private int exceptioncode ;// 异常code

    private String exceptiondetail ;// 异常详情

    private String actionmethod ;//请求方法

    private String params;//请求参数

    @Override
    public String toString() {
        return "SystemLogResponse{" +
                "id=" + id +
                ", requestip='" + requestip + '\'' +
                ", type='" + type + '\'' +
                ", userid=" + username +
                ", description='" + description + '\'' +
                ", actiondate=" + actiondate +
                ", exceptioncode=" + exceptioncode +
                ", exceptiondetail='" + exceptiondetail + '\'' +
                ", actionmethod='" + actionmethod + '\'' +
                ", params='" + params + '\'' +
                '}';
    }


    public SystemLogResponse(String requestip, String type, String username, String description, Date actiondate, int exceptioncode, String exceptiondetail, String actionmethod, String params) {
        this.requestip = requestip;
        this.type = type;
        this.username = username;
        this.description = description;
        this.actiondate = actiondate;
        this.exceptioncode = exceptioncode;
        this.exceptiondetail = exceptiondetail;
        this.actionmethod = actionmethod;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
