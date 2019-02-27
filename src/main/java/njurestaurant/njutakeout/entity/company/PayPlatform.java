package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;

/**
 * 存储用户的支付类型
 */
@Entity
@Table(name = "payPlatform")
public class PayPlatform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "codeCategory")
    private String codeCategory;

    public PayPlatform() {

    }

    public PayPlatform(String codeCategory) {
        this.codeCategory = codeCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(String codeCategory) {
        this.codeCategory = codeCategory;
    }
}
