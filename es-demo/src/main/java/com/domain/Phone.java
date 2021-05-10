package com.domain;

/**
 * @author soda
 * @date 2021/5/9
 */
public class Phone {

    private String name;
    private String brand;
    private double price;

    public Phone() {

    }

    public Phone(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
