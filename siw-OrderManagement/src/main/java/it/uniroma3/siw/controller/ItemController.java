package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.service.ItemService;
import it.uniroma3.siw.validator.ItemValidator;
import jakarta.validation.Valid;

@Controller
public class ItemController {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemValidator itemValidator;
	@Autowired
	ItemService itemService;

	@GetMapping("/admin/formNewItem")
	public String formNewItem(Model model) {
		model.addAttribute("item", new Item());
		return "admin/formNewItem.html";
	}

	@PostMapping("/admin/newItem")
	public String newItem(@Valid @ModelAttribute("item") Item item, @RequestParam("image") MultipartFile image,
			BindingResult bindingResult, Model model) {
		try{
			// prova salvtaggio immagine
			model.addAttribute("item", this.itemService.newItem(item, image, bindingResult));
			return "admin/item.html";
		} catch(IOException e) {
			return "admin/formNewItem.html";
		}
	}

	@GetMapping("/admin/removeItemPage")
	public String toRemoveItemPage(Model model) {
		model.addAttribute("items", this.itemService.findAllItems());
		return "admin/removeItems.html";
	}

	@GetMapping("/admin/removeItem/{id}")
	public String removeItem(@PathVariable("id") Long id, Model model) {
		this.itemService.deleteItem(id);
		model.addAttribute("items", this.itemService.findAllItems());
		return "admin/removeItems.html";
	}

	@GetMapping("/admin/items")
	public String showItems(Model model) {
		model.addAttribute("items", this.itemService.findAllItems());
		return "admin/items.html";
	}

	@GetMapping("/admin/items/{id}")
	public String getItem(@PathVariable("id") Long id, Model model) {
		model.addAttribute("item", this.itemService.getItem(id));
		return "admin/item.html";
	}

	@GetMapping("/admin/editItemPage")
	public String toEditItem(Model model) {
		model.addAttribute("items", this.itemService.findAllItems());
		return "admin/editItems.html";
	}

	@GetMapping("/admin/editItem/{id}")
	public String editItem(@PathVariable("id") Long id, Model model) {
		model.addAttribute("item", this.itemService.getItem(id));
		return "admin/formEditItem.html";
	}

	@PostMapping("/admin/editItem/{id}")
	public String saveItemChanges(@PathVariable Long id, @RequestParam("image") MultipartFile image,
			@Valid @ModelAttribute Item newitem,
			BindingResult bindingResult, Model model) {
		try{

			model.addAttribute("item", this.itemService.updateItem(id, image, newitem, bindingResult));
			return "admin/item.html";
		}
		catch(IOException e) {
			model.addAttribute("item", this.itemService.getItem(id));
			return "admin/formEditItem.html";
		}
	}
}
