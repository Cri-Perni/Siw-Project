package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.model.Ordination;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.repository.OrdinationRepository;


@Controller
public class OrdinationController {
	
	@Autowired ItemRepository itemRepository;
	@Autowired OrdinationRepository ordinationRepository;
	
	@GetMapping("/formNewOrder")
	public String formNewOrder(Model model) {
		Ordination order= new Ordination();
		
		this.ordinationRepository.save(order);
		
		model.addAttribute("order", order);
		model.addAttribute("items", this.itemRepository.findAll());
		return "formNewOrder.html";
	}
	
	@GetMapping("/addItemToOrder/{orderid}/{itemid}")
	public String addItemToOrder(@PathVariable("orderid") Long orderid,@PathVariable("itemid") Long itemid, Model model) {
		
		Ordination order = this.ordinationRepository.findById(orderid).get();
		OrderItem orderItem = new OrderItem();
		orderItem.setAll(order, this.itemRepository.findById(itemid).get(), 1);
		
		List<OrderItem> orderLines = (List<OrderItem>) order.getItems();
		
		if(orderLines.contains(orderItem)) {
		   orderLines.get(orderLines.indexOf(orderItem)).updateQuantity();
		}
		else {
			orderLines.add(orderItem);
		}
		
		order.setItems(orderLines);
		this.ordinationRepository.save(order);
		
		model.addAttribute("orderLines", orderLines);
		model.addAttribute("items", this.itemRepository.findAll());
		
		return "formNewOrder.html";
	}
	
	
	
	
	

}
