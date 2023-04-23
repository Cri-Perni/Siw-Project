package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Item;
import it.uniroma3.siw.repository.ItemRepository;

	@Component
	public class ItemValidator implements Validator{

		  @Autowired
		  private ItemRepository movieRepository;
		  
		  @Override
		  public void validate(Object o, Errors errors) {
			Item item = (Item)o;
		    if (item.getDescription()!=null && item.getPrice()!=null
				&& movieRepository.existsByDescriptionAndPrice(item.getDescription(), item.getPrice())) {
		      errors.reject("item.duplicate");
		    }
		   }
		  
		  @Override
		  public boolean supports(Class<?> aClass) {
		      return Item.class.equals(aClass);
		    }

}