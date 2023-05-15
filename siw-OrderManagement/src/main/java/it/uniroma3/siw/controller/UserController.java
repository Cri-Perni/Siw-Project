package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserValidator userValidator;
	@Autowired
	CredentialsRepository credentialsRepository;

	@GetMapping("/userPage")
	public String toStaffPage() {
		return "staff/waiterMenu.html";
	}

	@GetMapping("/admin/removeUserPage")
	public String toRemoveUserPage(Model model) {
		model.addAttribute("users", this.userRepository.findUsersByCredentialRole("DEFAULT"));
		return "admin/removeUser.html";
	}

	@GetMapping("/admin/removeUser/{id}")
	public String removeUser(@PathVariable("id") Long id, Model model) {
		User user = this.userRepository.findById(id).get();

		this.credentialsRepository.delete(user.getCredentials());
		this.userRepository.delete(user);

		model.addAttribute("users", this.userRepository.findUsersByCredentialRole("DEFAULT"));
		return "admin/removeUser.html";
	}

	@GetMapping("/admin/users")
	public String showUsers(Model model) {
		model.addAttribute("users", this.userRepository.findAll());
		return "admin/users.html";
	}

	@GetMapping("/admin/user/{id}")
	public String getUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", this.userRepository.findById(id).get());
		return "admin/user.html";
	}
}
