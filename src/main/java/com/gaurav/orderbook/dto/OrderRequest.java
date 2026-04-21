package com.gaurav.orderbook.dto;

import com.gaurav.orderbook.model.Side;
import lombok.Data;

@Data
public class OrderRequest {
    private double price;
    private int quantity;
    private Side side;
}