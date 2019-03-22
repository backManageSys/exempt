package njurestaurant.njutakeout.entity.account;

import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.publicdatas.app.CodeType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 供码用户
 */
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    /*申请人的id*/
    @Column(name = "applicantId")
    private int applicantId;
    /*添加事件*/
    @Column(name = "time")
    private Date time;
    /*审批事件*/
    @Column(name = "approvalTime")
    private Date approvalTime;
    /*审批人*/
    @Column(name = "approverId")
    private int approverId;
    /*审批状态*/
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "supplier", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Device> devices;
    /*供码者等级*/
    @Column(name = "priority")
    private int priority;
    // 供码用户当前选择的供码方式
    @Column(name = "payTypeId")
    private int payTypeId;
    @Transient
    private String codeCategory;
    @Transient
    private String codeType;
    @Transient
    private String applicant; //申请人

    public Supplier() {
    }

    public Supplier(User user, int applicantId, Date time, String status, List<Device> devices, int priority, int payTypeId) {
        this.user = user;
        this.applicantId = applicantId;
        this.time = time;
        this.status = status;
        this.devices = devices;
        this.priority = priority;
        this.payTypeId = payTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(String codeCategory) {
        this.codeCategory = codeCategory;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public void setApplicant(String applicant){this.applicant=applicant;}

    public String getApplicant(){
        return applicant;
    }
}
