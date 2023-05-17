package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.model.Ordination;
import it.uniroma3.siw.model.Sale;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.repository.OrderItemRepository;
import it.uniroma3.siw.repository.OrdinationRepository;
import it.uniroma3.siw.repository.SaleRepository;
import it.uniroma3.siw.service.ItemService;
import it.uniroma3.siw.service.OrdinationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrdinationController {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrdinationRepository ordinationRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    OrdinationService ordinationService;
    @Autowired
    ItemService itemService;

    @GetMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable("id") Long id, Model model) {

        /*Ordination order = this.ordinationRepository.findById(id).get();

        for(OrderItem orderLine: order.getItems()){
            orderLine.getItem().getOrder().remove(orderLine); //scollega la portata dalla riga di ordine
            orderLine.setItem(null); //scollega la riga di ordine dalla portata
            this.orderItemRepository.delete(orderLine); // rimuovi la riga di ordine
        }
        this.ordinationRepository.delete(order); // rimuovi l'ordine*/
        this.ordinationService.deleteOrder(id);

        return "staff/waiterMenu.html";
    }

    @GetMapping("/formNewOrder")
    public String formNewOrder(Model model) {
        /*Ordination order = new Ordination();

        order.setTotal((float) 0);
        order.setIsPaid(false);

        this.ordinationRepository.save(order);*/

        model.addAttribute("order", this.ordinationService.initializeOrder());
        model.addAttribute("items", this.itemService.findAllItems());
        return "staff/formNewOrder.html";
    }

    @GetMapping("/addItemToOrder/{orderid}/{itemid}")
    public String addItemToOrder(@PathVariable("orderid") Long orderid, @PathVariable("itemid") Long itemid,
            Model model) {

        /*Ordination order = this.ordinationRepository.findById(orderid).get();
        Item item = this.itemRepository.findById(itemid).get();

        // cerca se esiste già un OrderItem per la coppia Order e Item
        OrderItem orderItem = this.orderItemRepository.findByOrderAndItem(order, item);
        if (orderItem != null) {
            // se esiste, aumenta la quantità di 1
            orderItem.updateQuantity();
        } else {
            // se non esiste, crea una nuova OrderItem e aggiungila alle liste di Order
            // eItem
            orderItem = new OrderItem();
            orderItem.setAll(order, item, 1);
            order.getItems().add(orderItem);
            item.getOrder().add(orderItem);
        }

        // calcolo e aggiornamento del totale
        float total = 0;

        for (OrderItem orderLine : order.getItems()) {
            total = total + orderLine.getQuantity() * orderLine.getItem().getPrice();
        }

        order.setTotal(total);
        // rendi persistente l'oggetto OrderItem e le modifiche alle liste di Order e
        // Item
        this.orderItemRepository.save(orderItem);
        this.ordinationRepository.save(order);
        this.itemRepository.save(item);*/

        // aggiungi gli elementi alla vista
        model.addAttribute("order", this.ordinationService.addItemToOrder(orderid, itemid));
        model.addAttribute("items", this.itemService.findAllItems());

        return "staff/formNewOrder.html";
    }

    @PostMapping("/order/{id}")
    public String newOrder(@PathVariable Long id, @ModelAttribute Ordination neworder, Model model) {
       /*  // ottieni l'ordine a cui aggiungere il numero del tavolo
        Ordination order = this.ordinationRepository.findById(id).get();
        // aggiungi il valore tableNumber
        order.setTableNumber(neworder.getTableNumber());
        // rendi le modifiche persistenti
        this.ordinationRepository.save(order);*/
        // aggiungi l'ordine aggiornato alla vista
        model.addAttribute("order", this.ordinationService.newOrder(id, neworder));
        return "staff/order.html";
    }

    @GetMapping("/formPayment")
    public String toFormPayment(Model model) {
        // elimina tutte le righe ordine e gli ordini che fanno riferimento ad un tavolo nullo
        /*this.ordinationRepository.deleteAll(this.ordinationRepository.findByTableNumberIsNull());
         this.ordinationRepository.findByIsPaid(false);*/
        this.ordinationService.deleteAllNullOrders();
        model.addAttribute("orders", this.ordinationService.getOrdersToPay());
        return "staff/formPayment.html";
    }

    @GetMapping("/searchOrders")
    public String searchOrders(Model model, @RequestParam("tableNumber") Integer tableNumber) {
        model.addAttribute("orders",this.ordinationService.findByTableNumberAndIsPaidIsFalse(tableNumber));
        return "staff/formPayment.html";
    }

    @GetMapping("/toPayment/{tableNumber}")
    public String toPayment(@PathVariable("tableNumber") Integer tableNumber, Model model) {
        List<Ordination> orders =(List<Ordination>) this.ordinationService.findByTableNumberAndIsPaidIsFalse(tableNumber);
        List<OrderItem> orderLines = new ArrayList<OrderItem>();
        float total = 0;

        for (Ordination order : orders) {
            total = total + order.getTotal();
            for (OrderItem orderLine : order.getItems()) {
                orderLines.add(orderLine);
            }
        }

        model.addAttribute("tableNumber", tableNumber);
        model.addAttribute("total", total);
        model.addAttribute("orderLines", orderLines);
        return "staff/payOrder.html";
    }

    @GetMapping("/payOrder/{tableNumber}")
    public String payOrder(@PathVariable("tableNumber") Integer tableNumber) {
        this.ordinationService.payOrder(tableNumber);
        return "staff/waiterMenu.html";
    }
}
