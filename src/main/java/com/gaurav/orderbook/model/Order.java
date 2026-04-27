package com.gaurav.orderbook.model;

public class Order {

    private String id;
    private double price;
    private int quantity;
    private Side side;

    // GETTERS
    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Side getSide() {
        return side;
    }

    // SETTERS
    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}