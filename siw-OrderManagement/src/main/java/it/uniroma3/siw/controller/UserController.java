package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validator.UserValidator;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired UserRepository userRepository;
	@Autowired UserValidator userValidator;
	@Autowired CredentialsRepository credentialsRepository;
	
	  @GetMapping("/userPage")
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
	  
	  @GetMapping("/removeUserPage")
	  public String toRemoveUserPage(Model model) {
		  model.addAttribute("users", this.userRepository.findAll());
		  return "admin/removeUser.html";
	  }
	  
	  @GetMapping("/removeUser/{id}")
	  public String removeUser(@PathVariable("id") Long id, Model model) {
		  this.userRepository.deleteById(id);
		  
		  model.addAttribute("users", this.userRepository.findAll());
		  return "admin/removeUser.html";
	  }
	  
	  @GetMapping("/users")
	  public String showUsers(Model model) {
		  model.addAttribute("users", this.userRepository.findAll());
		  return "admin/users.html";
	  }
	  
	  @GetMapping("/user/{id}")
	  public String getUser(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("user", this.userRepository.findById(id).get());
	    return "admin/user.html";
	  }
}
