package com.gaurav.orderbook.controller;

import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import com.gaurav.orderbook.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<List<Trade>> placeOrder(
            @Valid @RequestBody Order order
    ) {

        log.info(
                "Incoming order | side={} price={} quantity={}",
                order.getSide(),
                order.getPrice(),
                order.getQuantity()
        );

        List<Trade> trades = orderService.processOrder(order);

        log.info("Generated {} trades", trades.size());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trades);
    }
}