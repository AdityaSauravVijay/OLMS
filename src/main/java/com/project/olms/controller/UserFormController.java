package com.project.olms.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.olms.dao.UserDAO;
import com.project.olms.pojo.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserFormController {
	
	@Autowired 
	private HttpSession httpSession;
	
	@GetMapping("/registration")
	public String showRegistrationForm(Model model, User user) {
		try {
			model.addAttribute("user", user);
			return "registration-form";
		}catch(Exception ex) {
			return "error";
		}
	}
	
	@PostMapping("/registration")
	public String handleRegistrationForm(@ModelAttribute("user") User user, UserDAO userDAO, Model model) {
		try {
			if(user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty() || user.getRole().isEmpty()) {
				model.addAttribute("error", "No field can be empty");
				return "registration-form";
			}
			else if(!isValidEmail(user.getEmail())) {
				model.addAttribute("error", "Invalid email format");
				return "registration-form";
			}
			else
				userDAO.saveOrUpdateUser(user);
			
			return "registered-successfully";
		} catch(Exception ex) {
			return "error";
		}
	}
	
	
	@GetMapping("/login")
	public String showLoginForm(Model model, User user) {
		try {
			model.addAttribute("user", user);
			return "login-form";
		}catch(Exception ex) {
			return "error";
		}
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("user") User user, UserDAO userDAO, Model model) {
		try {
			if(user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
				model.addAttribute("error", "Incorrect user name or password");
				return "login-form";
			}
			User existingUser = userDAO.getUserByEmail(user.getEmail());
			httpSession.setAttribute("session", existingUser);
			
			if(existingUser !=null && existingUser.getPassword().equals(user.getPassword()) && existingUser.getRole().equals("Librarian")) {
				return "librarian-home";
			}
			
			if(existingUser !=null && existingUser.getPassword().equals(user.getPassword()) && existingUser.getRole().equals("Reader")) {
				return "reader-home";
			}
			
			else {
				model.addAttribute("error", "invalid email or password");
				return "login-form";
			}
		} catch(Exception ex) {
			return "login-form-error";
		}
	}
	
	@GetMapping("/logout")
	public String logout() {
		try {
			if(httpSession!=null) {
				httpSession.invalidate();
				return "redirect:/login";
			}
			else
				return "login-need";
		} catch(Exception ex) {
			return "logout-form-error";
		}
	}
	
	private boolean isValidEmail(String email) {
		 String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
		 Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
		 return pattern.matcher(email).matches();
	}
	
	

}
