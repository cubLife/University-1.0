package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.ItemForm;
import com.gmail.sergick6690.service.ItemService;
import com.gmail.sergick6690.universityModels.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dev/items", produces = {"application/xml", "application/json"})
public class ItemRestController {
    private ItemService itemService;
    private ModelsCreator modelsCreator;

    @Autowired
    public ItemRestController(ItemService itemService, ModelsCreator modelsCreator) {
        this.itemService = itemService;
        this.modelsCreator = modelsCreator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item add(@RequestBody ItemForm itemForm) throws ServiceException {
        Item item = modelsCreator.createNewItem(itemForm);
        itemService.add(item);
        return item;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Item> showAllSubjects() throws ServiceException {
        return itemService.findAll();
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Item showById(@PathVariable("itemId") int id) throws ServiceException {
        return itemService.findById(id);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("itemId") int id) throws ServiceException {
        itemService.removeById(id);
    }
}
