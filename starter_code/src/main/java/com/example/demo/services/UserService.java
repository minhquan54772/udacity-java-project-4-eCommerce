package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.UserAlreadyExistsException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public User findById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        return userById.get();

    }

    public User findByUsername(String username) {
        Optional<User> userByUsername = userRepository.findByUsername(username);
        if (userByUsername.isEmpty()) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }
        return userByUsername.get();
    }

    public User createUser(CreateUserRequest createUserRequest) {
        Optional<User> userByUsername = userRepository.findByUsername(createUserRequest.getUsername());
        if (userByUsername.isPresent()) {
            throw new UserAlreadyExistsException("User with username " + createUserRequest.getUsername() + " already exists");
        }
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return user;
    }
}
