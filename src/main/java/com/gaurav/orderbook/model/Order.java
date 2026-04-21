package com.gaurav.orderbook.model;

import lombok.Data;

@Data
public class Order {
    private String id;
    private double price;
    private int quantity;
    private Side side;
    private long timestamp;
}