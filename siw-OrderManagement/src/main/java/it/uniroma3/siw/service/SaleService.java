package it.uniroma3.siw.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Sale;
import it.uniroma3.siw.repository.SaleRepository;
import jakarta.transaction.Transactional;

@Service
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Transactional
    public Iterable<Sale> findAllSales(){
        return saleRepository.findAll();
    }

    @Transactional
    public Long count(){
        return this.saleRepository.count();
    }

    @Transactional
    public Float getTotalProfitSince(LocalDate localDate){
        return this.saleRepository.getTotalProfitSince(localDate);
    }
    
}
