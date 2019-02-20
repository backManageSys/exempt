package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class FoodDetailLoadResponse extends Response {
    private List<String> pictures;
    private String portName;
    private String name;
    private int amount;
    private double price;
    private String description;
    private boolean hasSpecialChoice;
    private String[] specialChoices;

    public FoodDetailLoadResponse() {
    }

    public FoodDetailLoadResponse(List<String> pictures, String portName, String name, int amount, double price, String description, boolean hasSpecialChoice, String[] specialChoices) {
        this.pictures = pictures;
        this.portName = portName;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.hasSpecialChoice = hasSpecialChoice;
        this.specialChoices = specialChoices;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasSpecialChoice() {
        return hasSpecialChoice;
    }

    public void setHasSpecialChoice(boolean hasSpecialChoice) {
        this.hasSpecialChoice = hasSpecialChoice;
    }

    public String[] getSpecialChoices() {
        return specialChoices;
    }

    public void setSpecialChoices(String[] specialChoices) {
        this.specialChoices = specialChoices;
    }
}
