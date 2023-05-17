package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserValidator userValidator;
	@Autowired
	CredentialsRepository credentialsRepository;
	@Autowired
	UserService userService;
	@Autowired
	CredentialsService credentialsService;

	@GetMapping("/userPage")
	public String toStaffPage() {
		return "staff/waiterMenu.html";
	}

	@GetMapping("/admin/removeUserPage")
	public String toRemoveUserPage(Model model) {
		model.addAttribute("users", this.userService.getUsersByCredentialRole("DEFAULT"));
		return "admin/removeUser.html";
	}

	@GetMapping("/admin/removeUser/{id}")
	public String removeUser(@PathVariable("id") Long id, Model model) {
		User user = this.userService.getUser(id);

		
		this.credentialsService.delete(user.getCredentials());
		this.userService.delete(user);

		model.addAttribute("users", this.userService.getUsersByCredentialRole("DEFAULT"));
		return "admin/removeUser.html";
	}

	@GetMapping("/admin/users")
	public String showUsers(Model model) {
		model.addAttribute("users", this.userService.getAllUsers());
		return "admin/users.html";
	}

	@GetMapping("/admin/user/{id}")
	public String getUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", this.userService.getUser(id));
		return "admin/user.html";
	}
}
