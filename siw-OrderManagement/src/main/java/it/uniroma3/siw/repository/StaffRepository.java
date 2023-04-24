package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Staff;

public interface StaffRepository extends CrudRepository<Staff,Long>{
	public boolean existsByNameAndSurname(String name, String surname); 
}
