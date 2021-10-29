package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.AudienceRepository;
import com.gmail.sergick6690.Repository.ItemRepository;
import com.gmail.sergick6690.Repository.ScheduleRepository;
import com.gmail.sergick6690.Repository.SubjectRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.ItemForm;
import com.gmail.sergick6690.universityModels.Audience;
import com.gmail.sergick6690.universityModels.Item;
import com.gmail.sergick6690.universityModels.Schedule;
import com.gmail.sergick6690.universityModels.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class ItemService {
    private ItemRepository itemRepository;
   private SubjectRepository subjectRepository;
    private AudienceRepository audienceRepository;
    private ScheduleRepository scheduleRepository;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public ItemService(ItemRepository itemRepository, SubjectRepository subjectRepository, AudienceRepository audienceRepository,ScheduleRepository scheduleRepository) {
        this.itemRepository = itemRepository;
        this.subjectRepository = subjectRepository;
        this.audienceRepository = audienceRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public void add(Item item) throws ServiceException {
        if (item == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            itemRepository.save(item);
            DEBUG.debug((format("New item - %s was added", item.toString())));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't add item - " + item + e, e);
        }
    }

    public Item findById(int id) throws ServiceException {
        try {
            Item item = itemRepository.findById(id).get();
            DEBUG.debug(format("Item with id - %d was returned", id));
            return item;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Item not found - " + id + e, e);
        }
    }

    public List<Item> findAll() throws ServiceException {
        try {
            List<Item> itemList = itemRepository.findAll();
            DEBUG.debug("All items was returned");
            return itemList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any items " + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            itemRepository.delete(this.findById(id));
            DEBUG.debug(format("Item with id - %d is removed", id));
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove item with id - " + id + e, e);
        }
    }

    public Item createNewItem(ItemForm itemForm) throws ServiceException {
        Subject subject = subjectRepository.findById(itemForm.getSubjectId()).get();
        Audience audience = audienceRepository.findById(itemForm.getAudienceId()).get();
        Schedule schedule = scheduleRepository.findById(itemForm.getScheduleId()).get();
        String day = itemForm.getDay();
        int hour = itemForm.getHour();
        int duration = itemForm.getDuration();
        return new Item(subject, day, hour, audience, duration, schedule);
    }
}