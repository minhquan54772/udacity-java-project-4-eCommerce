package com.example.demo.controllers;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.responses.BaseResponse;
import com.example.demo.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Item>>> getItems() {
        return ResponseEntity.ok(new BaseResponse<>(true, itemService.getAllItems()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Item>> getItemById(@PathVariable Long id) {
        try {
            Item itemById = itemService.getItemById(id);
            return ResponseEntity.ok(new BaseResponse<Item>(true, itemById));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BaseResponse<List<Item>>> getItemsByName(@PathVariable String name) {
        try {
            List<Item> itemsByName = itemService.getItemsByName(name);
            return ResponseEntity.ok(new BaseResponse<>(true, itemsByName));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, e.getMessage()));
        }
    }
}
