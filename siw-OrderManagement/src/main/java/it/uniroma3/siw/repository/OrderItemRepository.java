package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem,Long>{

}
