package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.model.Ordination;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.repository.OrderItemRepository;
import it.uniroma3.siw.repository.OrdinationRepository;

@Controller
public class OrdinationController {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	OrdinationRepository ordinationRepository;
	@Autowired
	OrderItemRepository orderItemRepository;


	@GetMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable("id") Long id, Model model) {
		
		Ordination order = this.ordinationRepository.findById(id).get();
		List<Item> items = new ArrayList();
		
		for(OrderItem orderItem : order.getItems()){
			items.add(orderItem.getItem());
			orderItem.setItem(null);
		}

		for(Item item : items){
			item.getOrder().removeAll(order.getItems());
		}

		this.orderItemRepository.deleteAll(this.ordinationRepository.findById(id).get().getItems());
        this.ordinationRepository.deleteById(id);
        return "waiterMenu.html";
    }

	@GetMapping("/formNewOrder")
	public String formNewOrder(Model model) {
		Ordination order = new Ordination();
		
		order.setTotal((float)0);
		order.setIsPaid(false);

		this.ordinationRepository.save(order);

		model.addAttribute("order", order);
		model.addAttribute("items", this.itemRepository.findAll());
		return "formNewOrder.html";
	}

	@GetMapping("/addItemToOrder/{orderid}/{itemid}")
    public String addItemToOrder(@PathVariable("orderid") Long orderid, @PathVariable("itemid") Long itemid,
            Model model) {

        Ordination order = this.ordinationRepository.findById(orderid).get();
        Item item = this.itemRepository.findById(itemid).get();

        // cerca se esiste già un OrderItem per la coppia Order e Item
        OrderItem orderItem = this.orderItemRepository.findByOrderAndItem(order, item);
        if (orderItem != null) {
            // se esiste, aumenta la quantità di 1
            orderItem.updateQuantity();
        } else {
            // se non esiste, crea una nuova OrderItem e aggiungila alle liste di Order e Item
            orderItem = new OrderItem();
            orderItem.setAll(order, item, 1);
            order.getItems().add(orderItem);
            item.getOrder().add(orderItem);
        }
        
        //calcolo e aggiornamento del totale
        float total= 0;

        for(OrderItem orderLine: order.getItems()){
            total= total + orderLine.getQuantity() * orderLine.getItem().getPrice();
        }

        order.setTotal(total);
        // rendi persistente l'oggetto OrderItem e le modifiche alle liste di Order e Item
        this.orderItemRepository.save(orderItem);
        this.ordinationRepository.save(order);
        this.itemRepository.save(item);

        // aggiungi gli elementi alla vista
        model.addAttribute("order", order);
        model.addAttribute("items", this.itemRepository.findAll());

        return "formNewOrder.html";
    }
}
