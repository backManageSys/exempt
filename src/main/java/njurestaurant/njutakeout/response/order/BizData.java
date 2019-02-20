package njurestaurant.njutakeout.response.order;

public class BizData {
    private String reUrl;
    private String s;
    private String u;
    private String a;
    private String m;

    public BizData(String reUrl, String s, String u, String a, String m) {
        this.reUrl = reUrl;
        this.s = s;
        this.u = u;
        this.a = a;
        this.m = m;
    }

    public String getReUrl() {
        return reUrl;
    }

    public void setReUrl(String reUrl) {
        this.reUrl = reUrl;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
