package com.example.demo.controllers;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.responses.BaseResponse;
import com.example.demo.services.OrderService;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<BaseResponse<UserOrder>> submit(@PathVariable String username) {
		try {
			UserOrder order = orderService.submitOrder(username);
			return ResponseEntity.ok(new BaseResponse<>(true, order));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, e.getMessage()));
		}
	}

	@GetMapping("/history/{username}")
	public ResponseEntity<BaseResponse<List<UserOrder>>> getOrdersForUser(@PathVariable String username) {
		try {
			List<UserOrder> orders = orderService.getOrdersForUser(username);
			return ResponseEntity.ok(new BaseResponse<>(true, orders));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, e.getMessage()));
		}
	}
}
