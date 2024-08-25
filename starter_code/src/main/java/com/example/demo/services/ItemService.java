package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item with id " + id + " not found"));
    }

    public List<Item> getItemsByName(String name) {
        List<Item> items = itemRepository.findByName(name);
        if (items == null || items.isEmpty()) {
            throw new ItemNotFoundException("Item with name " + name + " not found");
        }
        return items;
    }
}
