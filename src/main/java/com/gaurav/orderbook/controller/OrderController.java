package com.gaurav.orderbook.controller;

import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import com.gaurav.orderbook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public List<Trade> placeOrder(@RequestBody Order order) {
        return orderService.processOrder(order);
    }
}