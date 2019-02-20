package njurestaurant.njutakeout.response.food;

public class SupplierFoodItem {
    private String url;
    private String name;
    private double price;
    private int id;

    public SupplierFoodItem() {
    }

    public SupplierFoodItem(String url, String name, double price, int id) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
