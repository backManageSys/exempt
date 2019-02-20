package njurestaurant.njutakeout.parameters.food;

public class FoodAddParameters {
    private String name;
    private String url;
    private String description;
    private double price;
    private boolean hasChoice;
    private String[] choice;
    private String portName;

    public FoodAddParameters() {
    }

    public FoodAddParameters(String name, String url, String description, double price, boolean hasChoice, String[] choice, String portName) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.price = price;
        this.hasChoice = hasChoice;
        this.choice = choice;
        this.portName = portName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
