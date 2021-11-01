package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.ItemForm;
import com.gmail.sergick6690.service.ItemService;
import com.gmail.sergick6690.universityModels.Item;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/items", produces = {"application/xml", "application/json"})
public class ItemRestController {
    private ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new item")
    public Item add(@RequestBody ItemForm itemForm) throws ServiceException {
        Item item = itemService.createNewItem(itemForm);
        itemService.add(item);
        return item;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all items")
    public List<Item> showAllItems() throws ServiceException {
        return itemService.findAll();
    }

    @GetMapping("/{item-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get item by id")
    public Item showById(@PathVariable("item-id") int id) throws ServiceException {
        return itemService.findById(id);
    }

    @DeleteMapping("/{item-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete item by id")
    public void deleteById(@PathVariable("item-id") int id) throws ServiceException {
        itemService.removeById(id);
    }
}
