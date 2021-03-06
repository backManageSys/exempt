package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

public class SupplierFoodDetailResponse extends Response {
    private int id;
    private String name;
    private String url;
    private double price;
    private boolean hasChoice;
    private String[] choice;
    private String portName;

    public SupplierFoodDetailResponse() {
    }

    public SupplierFoodDetailResponse(int id, String name, String url, double price, boolean hasChoice, String[] choice, String portName) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.price = price;
        this.hasChoice = hasChoice;
        this.choice = choice;
        this.portName = portName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasChoice() {
        return hasChoice;
    }

    public void setHasChoice(boolean hasChoice) {
        this.hasChoice = hasChoice;
    }

    public String[] getChoice() {
        return choice;
    }

    public void setChoice(String[] choice) {
        this.choice = choice;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
