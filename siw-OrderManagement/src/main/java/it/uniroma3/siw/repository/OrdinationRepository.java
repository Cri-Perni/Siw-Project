package it.uniroma3.siw.repository;

import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ordination;

public interface OrdinationRepository extends CrudRepository<Ordination,Long>{
   public ArrayList<Ordination> findByTableNumberAndIsPaid(Integer tableNumber, boolean isPaid);
}
