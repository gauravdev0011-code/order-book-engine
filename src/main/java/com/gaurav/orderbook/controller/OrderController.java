package com.gaurav.orderbook.controller;

import com.gaurav.orderbook.dto.OrderRequest;
import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import com.gaurav.orderbook.service.OrderService;
import com.gaurav.orderbook.util.IdGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public List<Trade> placeOrder(@RequestBody OrderRequest req) {

        Order order = new Order();
        order.setId(IdGenerator.generateId());
        order.setPrice(req.getPrice());
        order.setQuantity(req.getQuantity());
        order.setSide(req.getSide());
        order.setTimestamp(System.currentTimeMillis());

        return service.process(order);
    }
}