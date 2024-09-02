package com.example.demo.controllers;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.security.jwt.JwtTokenUtils;
import com.example.demo.security.services.UserDetailsServiceImpl;
import com.example.demo.services.CartService;
import com.example.demo.services.ItemService;
import com.example.demo.services.UserService;
import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CartController.class)
@EnableWebMvc
@WebAppConfiguration
@ContextConfiguration(classes = {WebSecurityConfig.class, UserDetailsServiceImpl.class, BCryptPasswordEncoder.class, JwtTokenUtils.class})
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
public class CartControllerTests {
    @Autowired
    private JacksonTester<ModifyCartRequest> json;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @MockBean
    private ItemService itemService;

    private ModifyCartRequest request;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(10);
        request.setUsername("testUser");

        cart = new Cart();
        cart.setId(1L);
        cart.addItem(new Item(1L, "Test Item 1", BigDecimal.TEN, "Description"));
    }

    @Test
    public void testAddToCart() throws Exception {
        when(cartService.addToCart(any(ModifyCartRequest.class))).thenReturn(cart);

        mockMvc.perform(post("/api/cart/addToCart")
                        .content(json.write(request).getJson())
                        .characterEncoding(CharEncoding.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.items.length()").value(cart.getItems().size()))
                .andExpect(jsonPath("$.data.total").value(cart.getTotal()));
    }

    @Test
    public void testAddToCart_UserNotFoundException() throws Exception {
        when(cartService.addToCart(any(ModifyCartRequest.class))).thenThrow(new UserNotFoundException("User not found: " + request.getUsername()));

        mockMvc.perform(post("/api/cart/addToCart")
                        .content(json.write(request).getJson())
                        .characterEncoding(CharEncoding.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not found: " + request.getUsername()));
    }

    @Test
    public void testAddToCart_ItemNotFoundException() throws Exception {
        when(cartService.addToCart(any(ModifyCartRequest.class))).thenThrow(new ItemNotFoundException("Item not found: " + request.getItemId()));

        mockMvc.perform(post("/api/cart/addToCart")
                        .content(json.write(request).getJson())
                        .characterEncoding(CharEncoding.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Item not found: " + request.getItemId()));
    }

    @Test
    public void testRemoveFromCart() throws Exception {
        when(cartService.removeFromCart(any(ModifyCartRequest.class))).thenReturn(cart);

        mockMvc.perform(post("/api/cart/removeFromCart")
                        .content(json.write(request).getJson())
                        .characterEncoding(CharEncoding.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.items.length()").value(cart.getItems().size()))
                .andExpect(jsonPath("$.data.total").value(cart.getTotal()));
    }

    @Test
    public void testRemoveFromCart_UserNotFoundException() throws Exception {
        when(cartService.removeFromCart(any(ModifyCartRequest.class))).thenThrow(new UserNotFoundException("User not found: " + request.getUsername()));

        mockMvc.perform(post("/api/cart/removeFromCart")
                        .content(json.write(request).getJson())
                        .characterEncoding(CharEncoding.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not found: " + request.getUsername()));
    }

    @Test
    public void testRemoveFromCart_ItemNotFoundException() throws Exception {
        when(cartService.removeFromCart(any(ModifyCartRequest.class))).thenThrow(new ItemNotFoundException("Item not found: " + request.getItemId()));

        mockMvc.perform(post("/api/cart/removeFromCart")
                        .content(json.write(request).getJson())
                        .characterEncoding(CharEncoding.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Item not found: " + request.getItemId()));
    }
}
