package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.model.OrderItem;
import it.uniroma3.siw.model.Ordination;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    public OrderItem findByOrderAndItem(Ordination order, Item item);

    public void deleteAllByOrder(Ordination order);

    @Query("SELECT i.id FROM OrderItem oi JOIN oi.item i GROUP BY i.id ORDER BY SUM(oi.quantity) DESC")
    public List<Long> findItemIdOrderByTotalQuantityDesc();

}
