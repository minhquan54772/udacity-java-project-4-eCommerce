package com.example.demo.services;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.UserPasswordException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, CartRepository cartRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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

        if (createUserRequest.getPassword() == null || createUserRequest.getPassword().isEmpty()) {
            throw new UserPasswordException("You must enter your password to create a new user");
        }

        if (createUserRequest.getPassword().length() < 8) {
            throw new UserPasswordException("Password must be at least 8 characters");
        }

        if (!Objects.equals(createUserRequest.getPassword(), createUserRequest.getConfirmPassword())) {
            throw new UserPasswordException("Passwords do not match");
        }

        user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return user;
    }
}
