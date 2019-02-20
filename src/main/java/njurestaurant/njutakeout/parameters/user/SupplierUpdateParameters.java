package njurestaurant.njutakeout.parameters.user;

public class SupplierUpdateParameters {
    private String name;
    private String password;
    private int level;
    private String codeType;
    private String status;

    public SupplierUpdateParameters(String name, String password, int level, String codeType, String status) {
        this.name = name;
        this.password = password;
        this.level = level;
        this.codeType = codeType;
        this.status = status;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
