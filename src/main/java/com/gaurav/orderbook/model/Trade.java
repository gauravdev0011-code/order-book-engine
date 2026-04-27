package com.gaurav.orderbook.model;

public class Trade {

    private String tradeId;
    private String buyOrderId;
    private String sellOrderId;
    private double price;
    private int quantity;
    private long timestamp;

    // GETTERS
    public String getTradeId() {
        return tradeId;
    }

    public String getBuyOrderId() {
        return buyOrderId;
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // SETTERS
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setBuyOrderId(String buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    public void setSellOrderId(String sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}