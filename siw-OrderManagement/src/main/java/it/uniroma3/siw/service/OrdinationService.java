package it.uniroma3.siw.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.model.Ordination;
import it.uniroma3.siw.model.Sale;
import it.uniroma3.siw.repository.ItemRepository;
import it.uniroma3.siw.repository.OrderItemRepository;
import it.uniroma3.siw.repository.OrdinationRepository;
import it.uniroma3.siw.repository.SaleRepository;
import jakarta.transaction.Transactional;

@Service
public class OrdinationService {

    @Autowired
    OrdinationRepository ordinationRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    SaleRepository saleRepository;




    @Transactional
    public void deleteOrder(Long orderId){

        Ordination order = this.ordinationRepository.findById(orderId).get();

        for(OrderItem orderLine: order.getItems()){
            orderLine.getItem().getOrder().remove(orderLine); //scollega la portata dalla riga di ordine
            orderLine.setItem(null); //scollega la riga di ordine dalla portata
            this.orderItemRepository.delete(orderLine); // rimuovi la riga di ordine
        }
        this.ordinationRepository.delete(order);
    }

    @Transactional
    public Ordination initializeOrder(){
        
        Ordination order = new Ordination();

        order.setTotal((float) 0);
        order.setIsPaid(false);

        this.ordinationRepository.save(order);
        
        return order;
    }

    @Transactional
    public Ordination addItemToOrder(Long orderId, Long itemId){

        Ordination order = this.ordinationRepository.findById(orderId).get();
        Item item = this.itemRepository.findById(itemId).get();

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
        this.itemRepository.save(item);

        return order;

    }

    @Transactional
    public Ordination newOrder(Long orderId, Ordination newOrder){
        // ottieni l'ordine a cui aggiungere il numero del tavolo
        Ordination order = this.ordinationRepository.findById(orderId).get();
        // aggiungi il valore tableNumber
        order.setTableNumber(newOrder.getTableNumber());
        // rendi le modifiche persistenti
        this.ordinationRepository.save(order);

        return order;
    }

    @Transactional
    public void deleteAllNullOrders(){
        this.ordinationRepository.deleteAll(this.ordinationRepository.findByTableNumberIsNull());
    }

    @Transactional
    public Iterable<Ordination> getOrdersToPay(){
        return this.ordinationRepository.findByIsPaid(false);
    }

    @Transactional
    public Iterable<Ordination> findByTableNumberAndIsPaidIsFalse(Integer tableNumber){
        return this.ordinationRepository.findByTableNumberAndIsPaid(tableNumber, false);
    }

    @Transactional
    public void payOrder(Integer tableNumber){
        Sale sale = new Sale();
        ArrayList<Ordination> orders = new ArrayList<>();

        float total = 0;
        this.saleRepository.save(sale);

        for (Ordination order : this.ordinationRepository.findByTableNumberAndIsPaid(tableNumber, false)) {
            order.setIsPaid(true);
            order.setSale(sale);
            total += order.getTotal();
            this.ordinationRepository.save(order);
            orders.add(order);
        }

        sale.setDate(LocalDate.now());
        sale.setTime(LocalTime.now());
        sale.setTotal(total);
        sale.setOrders(orders);
        this.saleRepository.save(sale);
    }
    
}
