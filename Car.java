package oop.project;

public class Car {

    private String name;
    private int year;
    private String color;
    private double price;
    private int quantity;

    public Car(String name, int year, String color, double price, int quantity) {
        this.name = name;
        this.year = year;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
