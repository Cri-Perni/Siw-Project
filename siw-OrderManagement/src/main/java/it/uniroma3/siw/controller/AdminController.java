package it.uniroma3.siw.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.repository.OrderItemRepository;
import it.uniroma3.siw.repository.SaleRepository;

@Controller
public class AdminController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    public static void loadManagerPageAttributes(Model model, OrderItemRepository orderItemRepository, ItemRepository itemRepository, SaleRepository saleRepository){
        /*Per la stampa dei tre prodotti più venduti*/
        List<Long> ids = orderItemRepository.findItemIdOrderByTotalQuantityDesc();
        int i=0;
        for(Long id : ids){
            if(i < 3){
                model.addAttribute("item"+(i+1), itemRepository.findById(id).get());
                i++;
                }else{break;}
        }
        
        /*Per la stampa del numero di vendite totali*/
        model.addAttribute("totalSales", saleRepository.count());
    }

    @GetMapping("/admin/managerPage")
	  public String toManagerPage(Model model) {
        loadManagerPageAttributes(model,orderItemRepository,itemRepository,saleRepository);
        return "admin/adminMenu.html";
	}
    
    @GetMapping("/admin/sales")
    public String showSales(Model model) {
    	model.addAttribute("sales", this.saleRepository.findAll());
    	return "admin/sales.html";
    }
    
}
