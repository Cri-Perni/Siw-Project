package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.validator.ItemValidator;
import jakarta.transaction.Transactional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemValidator itemValidator;

    @Transactional
    public Iterable<Item> findAllItems() {
        return this.itemRepository.findAll();
    }

    @Transactional
    public Item newItem(Item item, MultipartFile image, BindingResult bindingResult) {
        this.itemValidator.validate(item, bindingResult);
        if (!bindingResult.hasErrors()) {
            // prova salvtaggio immagine
            try {
                String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
                item.setImageString(base64Image);
            } catch (IOException e) {
            }

            this.itemRepository.save(item);
        }
        return item;

    }

    @Transactional
    public Item getItem(Long id) {
        return this.itemRepository.findById(id).get();
    }

    @Transactional
    public void deleteItem(Long id) {
        Item item = this.itemRepository.findById(id).get();

        for (OrderItem orderLine : item.getOrder()) {
            orderLine.setItem(null);
        }

        this.itemRepository.delete(item);
    }

    @Transactional
    public Item updateItem(Long id, MultipartFile image, Item newItem, BindingResult bindingResult) {

        this.itemValidator.validate(newItem, bindingResult);

        if (!bindingResult.hasErrors()) {
            Item item = this.itemRepository.findById(id).get();

            item.setDescription(newItem.getDescription());
            item.setPrice(newItem.getPrice());

            try {
                String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
                item.setImageString(base64Image);
            } catch (IOException e) {}

            this.itemRepository.save(item);
            return item;
        }else{
            return newItem;
        }
    }
}
