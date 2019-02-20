package njurestaurant.njutakeout.parameters.company;

public class SystemAddParameters {
//    private int id;
    private String title;

    public SystemAddParameters(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
