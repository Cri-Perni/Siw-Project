package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Staff;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.StaffRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validator.StaffValidator;
import jakarta.validation.Valid;

@Controller
public class StaffController {
	
	@Autowired StaffRepository staffRepository;
	@Autowired StaffValidator staffValidator;
	@Autowired CredentialsRepository credentialsRepository;
	
	  @GetMapping("/staffPage")
	  public String toStaffPage() {
		  return "waiterMenu.html";
	  }
	  
	  
	  
	  ///
	  /*@GetMapping("/formNewStaff")
	  public String formNewstaff(Model model){
		  model.addAttribute("user", new User());
		  model.addAttribute("credentials", new User());
		  return "admin/formNewStaff.html";
	  }
	  
	  @PostMapping("/newStaff")
	  public String newStaff(@Valid @ModelAttribute("staff") Staff staff, BindingResult bindingResult, Model model) {
		  this.staffValidator.validate(staff,bindingResult);
		  if(!bindingResult.hasErrors())
		  {
	      this.staffRepository.save(staff);
	      model.addAttribute("staff", staff);
	      return "staffMember.html";
	    } 
	    else
	    {
	      return "admin/formNewStaff.html";
	    }
	  }*/
	  
	  @GetMapping("/removeStaffPage")
	  public String toRemoveStaffPage(Model model) {
		  model.addAttribute("staff", this.staffRepository.findAll());
		  return "admin/removeStaff.html";
	  }
	  
	  @GetMapping("/removeStaff/{id}")
	  public String removestaff(@PathVariable("id") Long id, Model model) {
		  Staff staff= this.staffRepository.findById(id).get();
		  this.staffRepository.delete(staff);
		  
		  model.addAttribute("staff", this.staffRepository.findAll());
		  return "admin/removeStaff.html";
	  }
	  
	  @GetMapping("/staff")
	  public String showStaff(Model model) {
		  model.addAttribute("staff", this);
		  return "staff.html";
	  }
	  
	  @GetMapping("/staff/{id}")
	  public String getStaff(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("staff", this.staffRepository.findById(id).get());
	    return "staffMember.html";
	  }
}
