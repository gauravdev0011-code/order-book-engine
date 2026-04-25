package com.gaurav.orderbook.controller;

import com.gaurav.orderbook.dto.OrderRequest;
import com.gaurav.orderbook.dto.TradeResponse;
import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import com.gaurav.orderbook.service.OrderService;
import com.gaurav.orderbook.util.IdGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public List<TradeResponse> placeOrder(@RequestBody OrderRequest request) {

        Order order = new Order();
        order.setId(IdGenerator.generateId());
        order.setPrice(request.getPrice());
        order.setQuantity(request.getQuantity());
        order.setSide(request.getSide());

        List<Trade> trades = orderService.process(order);

        // ✅ Convert to DTO (THIS FIXES YOUR WARNING)
        return trades.stream().map(trade -> {
            TradeResponse res = new TradeResponse();
            res.setTradeId(trade.getTradeId());
            res.setPrice(trade.getPrice());
            res.setQuantity(trade.getQuantity());
            return res;
        }).collect(Collectors.toList());
    }
}