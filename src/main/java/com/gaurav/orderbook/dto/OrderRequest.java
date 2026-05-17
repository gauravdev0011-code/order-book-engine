package com.gaurav.orderbook.dto;

import com.gaurav.orderbook.model.Side;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Incoming order request payload
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderRequest {

    @Positive(message = "Price must be greater than 0")
    private double price;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;

    @NotNull(message = "Side is required")
    private Side side;
}