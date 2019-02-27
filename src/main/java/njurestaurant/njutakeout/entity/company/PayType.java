package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;

/**
 * 存储用户的支付类型
 */
@Entity
@Table(name = "PayType")
public class PayType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "codeCategory")
    private String codeCategory;
    @Column(name = "codeType")
    private String codeType;
    @Column(name = "status")
    private String status;

    public PayType() {

    }

    public PayType(String codeCategory, String codeType, String status) {
        this.codeCategory = codeCategory;
        this.codeType = codeType;
        this.status = status;
    }

    public int getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
