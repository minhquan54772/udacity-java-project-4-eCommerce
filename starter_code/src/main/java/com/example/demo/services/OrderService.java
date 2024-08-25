package com.example.demo.services;

import com.example.demo.exceptions.OrderCreationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public UserOrder submitOrder(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found: " + username);
        }
        UserOrder order = UserOrder.createFromCart(user.get().getCart());
        if (order == null) {
            // may not occur this case
            throw new OrderCreationException("Order creation failed");
        }
        return orderRepository.save(order);
    }

    public List<UserOrder> getOrdersForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found: " + username);
        }
        return orderRepository.findByUser(user.get());
    }
}
