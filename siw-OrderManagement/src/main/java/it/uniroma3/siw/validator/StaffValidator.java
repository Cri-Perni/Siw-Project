package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Staff;
import it.uniroma3.siw.repository.StaffRepository;

	@Component
	public class StaffValidator implements Validator{

		  @Autowired
		  private StaffRepository staffRepository;
		  
		  @Override
		  public void validate(Object o, Errors errors) {
			Staff staff = (Staff)o;
		    if (staff.getName()!=null && staff.getSurname()!=null
				&& staffRepository.existsByNameAndSurname(staff.getName(), staff.getSurname())) {
		      errors.reject("staff.duplicate");
		    }
		   }
		  
		  @Override
		  public boolean supports(Class<?> aClass) {
		      return Staff.class.equals(aClass);
		    }

}