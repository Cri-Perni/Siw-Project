package it.uniroma3.siw.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.repository.OrderItemRepository;
import it.uniroma3.siw.repository.OrdinationRepository;

@Controller
public class AdminController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrdinationRepository ordinationRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @GetMapping("/managerPage")
	  public String toManagerPage(Model model) {
        List<Long> ids = this.orderItemRepository.findItemIdOrderByTotalQuantityDesc();
        
        int i=0;
        for(Long id : ids){
            if(i < 3){
                model.addAttribute("item"+(i+1), this.itemRepository.findById(id).get());
                i++;
                }else{break;}
        }
		
        return "adminMenu.html";
	}
    
}
