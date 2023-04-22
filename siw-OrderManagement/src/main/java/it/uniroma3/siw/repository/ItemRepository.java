package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Item;

public interface ItemRepository extends CrudRepository<Item,Long>{

}
