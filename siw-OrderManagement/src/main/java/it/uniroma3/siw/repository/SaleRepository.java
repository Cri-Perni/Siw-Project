package it.uniroma3.siw.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Sale;

public interface SaleRepository extends CrudRepository<Sale,Long>{
    @Query(value="SELECT SUM(s.total) FROM Sale s")
    public Float getTotalProfit();

    @Query(value="SELECT SUM(s.total) FROM Sale s WHERE s.date >= :date")
    public Float getTotalProfitSince(@Param("date") LocalDate date);
}
