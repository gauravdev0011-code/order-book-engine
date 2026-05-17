package com.gaurav.orderbook.controller;

import com.gaurav.orderbook.dto.OrderRequest;
import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import com.gaurav.orderbook.service.OrderService;
import com.gaurav.orderbook.util.IdGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for order submission
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<List<Trade>> placeOrder(
            @Valid @RequestBody OrderRequest request
    ) {

        log.info(
                "Incoming order request | side={} price={} quantity={}",
                request.getSide(),
                request.getPrice(),
                request.getQuantity()
        );

        // DTO -> Domain mapping
        Order order = Order.builder()
                .id(IdGenerator.generateId())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .side(request.getSide())
                .timestamp(System.currentTimeMillis())
                .build();

        List<Trade> trades = orderService.processOrder(order);

        log.info(
                "Order processed successfully | tradesGenerated={}",
                trades.size()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trades);
    }
}