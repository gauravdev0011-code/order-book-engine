package com.gaurav.orderbook.dto;

import lombok.Data;

@Data
public class TradeResponse {
    private String tradeId;
    private double price;
    private int quantity;
}