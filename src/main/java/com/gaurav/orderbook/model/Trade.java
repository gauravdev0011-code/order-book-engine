package com.gaurav.orderbook.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Trade {

    @NotBlank
    private String tradeId;

    @NotBlank
    private String buyOrderId;

    @NotBlank
    private String sellOrderId;

    @Positive
    private double price;

    @Positive
    private int quantity;

    private long timestamp;
}