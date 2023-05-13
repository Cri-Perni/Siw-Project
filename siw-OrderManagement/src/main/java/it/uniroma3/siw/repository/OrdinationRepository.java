package it.uniroma3.siw.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Ordination;

public interface OrdinationRepository extends CrudRepository<Ordination, Long> {

   public ArrayList<Ordination> findByTableNumberAndIsPaid(Integer tableNumber, boolean isPaid);
   public ArrayList<Ordination> findByIsPaid(boolean isPaid);

   public List<Ordination> findByTableNumberIsNull();

   @Query(value = "SELECT new Ordination( o.isPaid,o.tableNumber, SUM(o.total)) FROM Ordination o WHERE o.isPaid = false AND o.isPaid IS NOT NULL  GROUP BY o.tableNumber", nativeQuery = true)
   List<Ordination> findNotPaidTableTotals();

   @Query(value= "SELECT new Ordination( o.isPaid, o.tableNumber, SUM(o.total)) FROM Ordination o WHERE o.isPaid = false AND o.isPaid IS NOT NULL AND o.tableNumber = :table GROUP BY o.tableNumber", nativeQuery = true)
   Ordination findNotPaidTableTotalByTableNumber(@Param("table") Integer tableNumber);
}
