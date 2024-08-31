package com.example.demo.services;

import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Cart addToCart(ModifyCartRequest request) {
        Cart cart = this.getCart(request);
        Item item = getItem(request);
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(item));
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    public Cart removeFromCart(ModifyCartRequest request) {
        Cart cart = this.getCart(request);
        Item item = getItem(request);
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(item));
        cartRepository.save(cart);
        return cart;
    }

    private Cart getCart(ModifyCartRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found: " + request.getUsername());
        }
        return user.get().getCart();
    }

    private Item getItem(ModifyCartRequest request) {
        Optional<Item> item = itemRepository.findById(request.getItemId());
        if (item.isEmpty()) {
            throw new ItemNotFoundException("Item not found: " + request.getItemId());
        }
        return item.get();
    }
}
