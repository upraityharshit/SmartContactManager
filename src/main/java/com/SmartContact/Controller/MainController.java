package com.SmartContact.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SmartContact.Entity.User;
import com.SmartContact.helper.Message;
import com.SmartContact.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "about - Smart Contact Manager");	
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model,HttpSession session) {
		model.addAttribute("title", "SignUp - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/do_register")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, 
			@RequestParam(value ="enabled",defaultValue = "false") boolean enabled,
			Model model, HttpSession session) {
		
		try {
			
			if(!enabled) {
				
				throw new Exception();
			}
			
			if(result.hasErrors())
			{
				System.out.println(result);
				throw new Exception();
				
			}
			else {
				
				user.setEnabled(enabled);
				user.setRole("User");
				user.setImage("default.png");
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
				//save the new user
				
				userRepository.save(user);
				
				model.addAttribute("user", new User());
				session.setAttribute("message", new Message("Successfully Registered !!","alert-success"));
				
				System.out.println(enabled);
				System.out.println(user);
	
				return "signup";
			}
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong!!","alert-danger"));
			
			
			return "signup";
		}
		
	}
	
	@GetMapping("/signin")
	public String Login(Model model) {
		
		model.addAttribute("title", "Login");
		return "Login";
	}
	
}
