package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.model.persistence.Item;
import com.example.demo.services.ItemService;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemService.getAllItems());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		return ResponseEntity.ok(itemService.getItemById(id));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		return ResponseEntity.ok(itemService.getItemsByName(name));
	}
}
