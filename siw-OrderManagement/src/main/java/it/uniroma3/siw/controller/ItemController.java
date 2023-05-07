package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.validator.ItemValidator;
import jakarta.validation.Valid;

@Controller
public class ItemController {
	
	@Autowired ItemRepository itemRepository;
	@Autowired ItemValidator itemValidator;
	
	//sono presenti implementazioni di richieste momentanee, saranno spostate in ManagerController e StaffController
	  /*@GetMapping("/index")
	  public String toIndex() {
	    return "index.html";
	  }*/
	  
	  @GetMapping(value="/login")
	  public String toLogin() {
		  return "login.html";  
	  }
	  
	  @GetMapping("/error")
	  public String errorPage() {
		  return "notFound.html";
	  }
	  
	  ///
	  @GetMapping("/formNewItem")
	  public String formNewItem(Model model){
		  model.addAttribute("item", new Item());
		  return "admin/formNewItem.html";
	  }
	  
	  @PostMapping("/newItem")
	  public String newMovie(@Valid @ModelAttribute("item") Item item, BindingResult bindingResult, Model model) {
		  this.itemValidator.validate(item,bindingResult);
		  if(!bindingResult.hasErrors())
		  {
	      this.itemRepository.save(item);
	      model.addAttribute("item", item);
	      return "admin/item.html";
	    } 
	    else
	    {
	      return "admin/formNewItem.html";
	    }
	  }
	  
	  @GetMapping("/removeItemPage")
	  public String toRemoveItemPage(Model model) {
		  model.addAttribute("items", this.itemRepository.findAll());
		  return "admin/removeItems.html";
	  }
	  
	  @GetMapping("/removeItem/{id}")
	  public String removeItem(@PathVariable("id") Long id, Model model) {
		  Item item= this.itemRepository.findById(id).get();
		  
		  //scollega tutte le righe di ordine dalla portata da rimuovere
		  for(OrderItem orderLine: item.getOrder()){
			orderLine.setItem(null);
		  }

		  this.itemRepository.delete(item);
		  
		  model.addAttribute("items", this.itemRepository.findAll());
		  return "admin/removeItems.html";
	  }
	  
	  @GetMapping("/items")
	  public String showItems(Model model) {
		  model.addAttribute("items", this.itemRepository.findAll());
		  return "admin/items.html";
	  }
	  
	  @GetMapping("/items/{id}")
	  public String getItem(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("item", this.itemRepository.findById(id).get());
	    return "admin/item.html";
	  }
}
