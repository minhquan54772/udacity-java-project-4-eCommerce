package com.example.demo.controllers;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.model.persistence.Item;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.security.jwt.JwtTokenUtils;
import com.example.demo.security.services.UserDetailsServiceImpl;
import com.example.demo.services.ItemService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@EnableWebMvc
@WebAppConfiguration
@SpringBootTest(classes = ItemController.class)
@ContextConfiguration(classes = {WebSecurityConfig.class, UserDetailsServiceImpl.class, BCryptPasswordEncoder.class, JwtTokenUtils.class})
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetItems() throws Exception {
        List<Item> items = Arrays.asList(new Item(1L, "Item1", BigDecimal.TEN, "Description1"), new Item(2L, "Item2", BigDecimal.TEN, "Description2"));
        given(itemService.getAllItems()).willReturn(items);

        mockMvc.perform(get("/api/item"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.length()").value(items.size()));
    }

    @Test
    public void testGetItemById_Success() throws Exception {
        Item item = new Item(1L, "Item1", BigDecimal.TEN, "Description1");
        given(itemService.getItemById(anyLong())).willReturn(item);

        mockMvc.perform(get("/api/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(item.getId()))
                .andExpect(jsonPath("$.data.name").value(item.getName()));
    }

    @Test
    public void testGetItemById_NotFound() throws Exception {
        given(itemService.getItemById(anyLong())).willThrow(new ItemNotFoundException("Item not found"));

        mockMvc.perform(get("/api/item/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Item not found"));
    }

    @Test
    public void testGetItemsByName_Success() throws Exception {
        List<Item> items = Arrays.asList(new Item(1L, "Item1", BigDecimal.TEN, "Description1"), new Item(2L, "Item1", BigDecimal.ONE, "Description2"));
        given(itemService.getItemsByName(anyString())).willReturn(items);

        mockMvc.perform(get("/api/item/name/Item1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(items.size()));
    }

    @Test
    public void testGetItemsByName_NotFound() throws Exception {
        given(itemService.getItemsByName(anyString())).willThrow(new ItemNotFoundException("Items not found"));

        mockMvc.perform(get("/api/item/name/Item1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Items not found"));
    }
}
