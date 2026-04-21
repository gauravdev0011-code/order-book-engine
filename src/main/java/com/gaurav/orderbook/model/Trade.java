package com.gaurav.orderbook.model;

import lombok.Data;

@Data
public class Trade {
    private String tradeId;
    private String buyOrderId;
    private String sellOrderId;
    private double price;
    private int quantity;
    private long timestamp;
}