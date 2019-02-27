package njurestaurant.njutakeout.parameters.company;

public class PayTypeAddParameters {
    private String codeCategory;
    private String codeType;
    private String status;

    public PayTypeAddParameters() {
    }

    public PayTypeAddParameters(String codeCategory, String codeType, String status) {
        this.codeCategory = codeCategory;
        this.codeType = codeType;
        this.status = status;
    }

    public String getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(String codeCategory) {
        codeCategory = codeCategory;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        codeType = codeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
