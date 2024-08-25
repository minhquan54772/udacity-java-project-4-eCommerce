package com.example.demo.controllers;

import com.example.demo.model.persistence.UserOrder;
import com.example.demo.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		UserOrder order = orderService.submitOrder(username);
		return ResponseEntity.ok(order);
	}

	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		List<UserOrder> orders = orderService.getOrdersForUser(username);
		return ResponseEntity.ok(orders);
	}
}
