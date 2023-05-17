package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.OrderItemRepository;

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    public List<Long> getItemIdOrderByTotalQuantityDesc(){
        return (List<Long>) orderItemRepository.findItemIdOrderByTotalQuantityDesc();
    }
    
}
