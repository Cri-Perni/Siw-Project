package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.model.Ordination;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    public OrderItem findByOrderAndItem(Ordination order, Item item);

    public void deleteAllByOrder(Ordination order);

}
