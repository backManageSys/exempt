package njurestaurant.njutakeout.parameters.company;

public class PayPlatformAddParameters {
    private String codeCategory;

    public PayPlatformAddParameters() {
    }

    public PayPlatformAddParameters(String codeCategory) {
        codeCategory = codeCategory;
    }

    public String getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(String codeCategory) {
        codeCategory = codeCategory;
    }
}
