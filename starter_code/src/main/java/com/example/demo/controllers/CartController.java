package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
		Cart cart = cartService.addToCart(request);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
		Cart cart = cartService.removeFromCart(request);
		return ResponseEntity.ok(cart);
	}
}
