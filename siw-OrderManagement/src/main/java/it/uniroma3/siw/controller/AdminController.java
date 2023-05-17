package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.ItemService;
import it.uniroma3.siw.service.OrderItemService;
import it.uniroma3.siw.service.SaleService;

@Controller
public class AdminController {
    @Autowired
    SaleService saleService;
    @Autowired
    OrderItemService OrderItemService;
    @Autowired
    ItemService itemService;

    public static void loadManagerPageAttributes(Model model, OrderItemService OrderItemService, ItemService itemService, SaleService saleService){
        /*Per la stampa dei tre prodotti più venduti*/
        List<Long> ids = OrderItemService.getItemIdOrderByTotalQuantityDesc();
        int i=0;
        for(Long id : ids){
            if(i < 3){
                model.addAttribute("item"+(i+1), itemService.getItem(id));
                i++;
                }else{break;}
        }
        
        /*Per la stampa del numero di vendite totali*/
        model.addAttribute("totalSales", saleService.count());
        /*vengono calcolati i profitti settimanali e mensili e sono aggiunti al template*/
        model.addAttribute("weeklyProfit", saleService.getTotalProfitSince(LocalDate.now().minusMonths(1)));
        model.addAttribute("monthlyProfit", saleService.getTotalProfitSince(LocalDate.now().minusWeeks(1)));
    }

    @GetMapping("/admin/managerPage")
	  public String toManagerPage(Model model) {
        loadManagerPageAttributes(model,OrderItemService,itemService,saleService);
        return "admin/adminMenu.html";
	}
    
    @GetMapping("/admin/sales")
    public String showSales(Model model) {
    	model.addAttribute("sales", this.saleService.findAllSales());
    	return "admin/sales.html";
    }
    
}
