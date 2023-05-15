package it.uniroma3.siw.controller;

import java.io.Console;
import java.io.IOException;
import java.util.Base64;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.validator.ItemValidator;
import jakarta.validation.Valid;

@Controller
public class ItemController {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemValidator itemValidator;

	@GetMapping("/admin/formNewItem")
	public String formNewItem(Model model) {
		model.addAttribute("item", new Item());
		return "admin/formNewItem.html";
	}

	@PostMapping("/admin/newItem")
	public String newMovie(@Valid @ModelAttribute("item") Item item,@RequestParam("image") MultipartFile image, BindingResult bindingResult, Model model) {
		this.itemValidator.validate(item, bindingResult);
		if (!bindingResult.hasErrors()) {
			// prova salvtaggio immagine
			try{
			String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
			item.setImageString(base64Image);
			}catch(IOException e){}

			this.itemRepository.save(item);
			model.addAttribute("item", item);
			return "admin/item.html";
		} else {
			return "admin/formNewItem.html";
		}
	}

	@GetMapping("/admin/removeItemPage")
	public String toRemoveItemPage(Model model) {
		model.addAttribute("items", this.itemRepository.findAll());
		return "admin/removeItems.html";
	}

	@GetMapping("/admin/removeItem/{id}")
	public String removeItem(@PathVariable("id") Long id, Model model) {
		Item item = this.itemRepository.findById(id).get();

		// scollega tutte le righe di ordine dalla portata da rimuovere
		for (OrderItem orderLine : item.getOrder()) {
			orderLine.setItem(null);
		}

		this.itemRepository.delete(item);

		model.addAttribute("items", this.itemRepository.findAll());
		return "admin/removeItems.html";
	}

	@GetMapping("/admin/items")
	public String showItems(Model model) {
		model.addAttribute("items", this.itemRepository.findAll());
		return "admin/items.html";
	}

	@GetMapping("/admin/items/{id}")
	public String getItem(@PathVariable("id") Long id, Model model) {
		model.addAttribute("item", this.itemRepository.findById(id).get());
		return "admin/item.html";
	}

	@GetMapping("/admin/editItemPage")
	public String toEditItem(Model model) {
		model.addAttribute("items", this.itemRepository.findAll());
		return "admin/editItems.html";
	}

	@GetMapping("/admin/editItem/{id}")
	public String editItem(@PathVariable("id") Long id, Model model) {
		model.addAttribute("item", this.itemRepository.findById(id).get());
		return "admin/formEditItem.html";
	}

	@PostMapping("/admin/editItem/{id}")
	public String saveItemChanges(@PathVariable Long id,@RequestParam("image") MultipartFile image, @Valid @ModelAttribute Item newitem,
			BindingResult bindingResult, Model model) {
		this.itemValidator.validate(newitem, bindingResult);

		if (!bindingResult.hasErrors()) {
			Item item = this.itemRepository.findById(id).get();

			item.setDescription(newitem.getDescription());
			item.setPrice(newitem.getPrice());
			
			try{
				String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
				item.setImageString(base64Image);
				}catch(IOException e){}

			this.itemRepository.save(item);
			model.addAttribute("item", item);
			return "admin/item.html";
		} else {
			model.addAttribute("item", newitem);
			return "admin/formEditItem.html";
		}
	}
}
