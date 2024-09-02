package com.example.demo.controllers;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.model.responses.BaseResponse;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<BaseResponse<Cart>> addToCart(@RequestBody ModifyCartRequest request) {
        try {
            Cart cart = cartService.addToCart(request);
            return ResponseEntity.ok(new BaseResponse<>(true, cart));
        } catch (UserNotFoundException | ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<BaseResponse<Cart>> removeFromCart(@RequestBody ModifyCartRequest request) {
        try {
            Cart cart = cartService.removeFromCart(request);
            return ResponseEntity.ok(new BaseResponse<>(true, cart));
        } catch (UserNotFoundException | ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, e.getMessage()));
        }
    }
}
