package com.SmartContact.Controller;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SmartContact.Entity.Contact;
import com.SmartContact.Entity.User;
import com.SmartContact.helper.Message;
import com.SmartContact.repository.ContactRepository;
import com.SmartContact.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute
	public void addcommondata(Model model, Principal principal) {
		String username = principal.getName();

		User user = userRepository.getUserByUserName(username);

		//System.out.println("User " + user);
		model.addAttribute("user", user);

	}

	@GetMapping("/index")
	public String index(Model model) {

		model.addAttribute("title", "User Dashboard");
		return "User/user_dashboard";

	}

	@GetMapping("/add-Contact")
	public String openaddcontactform(Model model) {

		model.addAttribute("title", "Add Contact form");
		model.addAttribute("contact", new Contact());

		return "User/add_contact_form";
	}

	//save the new contact
	
	@PostMapping("/do_contact")
	public String saveContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result,
			Principal principal, Model model, HttpSession session, @RequestParam("profileImage") MultipartFile image) {

		try {

			if (result.hasErrors()) {
				System.out.println(result);
				throw new Exception();

			} else {
				System.out.println(contact);

				String name = principal.getName();
				User user = this.userRepository.getUserByUserName(name);

				if (image.isEmpty()) {

					System.out.println("Image is empty");
					contact.setImage("noContact.png");

				} else {
					contact.setImage(image.getOriginalFilename());

					String absolutePath = new ClassPathResource("static/image").getFile().getAbsolutePath();

					Path path = Paths.get(absolutePath + File.separator + image.getOriginalFilename());

					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				}

				user.getContact().add(contact);
				contact.setUser(user);

				// save the new contact
				userRepository.save(user);

				model.addAttribute("contact", new Contact());
				session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));

				return "User/add_contact_form";
			}

		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("contact", contact);
			session.setAttribute("message", new Message("Something went wrong!!", "alert-danger"));

			return "User/add_contact_form";
		}

	}
	
	//open the view contact form
	
	@GetMapping("/view-Contacts/{page}")
	public String viewcontact(@PathVariable("page") Integer page, Model model, Principal principal) {

		String username = principal.getName();
		User user = this.userRepository.getUserByUserName(username);

		Pageable pageable = PageRequest.of(page, 6);

		Page<Contact> contacts = this.contactRepository.findContactByUser(user.getUid(), pageable);

		model.addAttribute("title", "View Contacts");
		model.addAttribute("contact", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());

		return "User/view-Contact";
	}
	
	//open the form to view the contact profile
	
	@GetMapping("/{cid}/view-contactProfile")
	public String viewprofile(@PathVariable("cid") Integer id, Model model, Principal principal){
		
		Contact contact = this.contactRepository.findById(id).get();
		
		String username = principal.getName();
		User user = this.userRepository.getUserByUserName(username);
		
		if(user.getUid()==contact.getUser().getUid())
			model.addAttribute("contact", contact);
		
		model.addAttribute("title", "Contact Profile");

		return "User/view-contactProfile";
	}
	
	//open the form to view the User profile
	
	@GetMapping("/view-userProfile")
	public String viewuserprofile(Principal principal, Model model){
		
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		
		model.addAttribute("title", "User Profile");
		model.addAttribute("user", user);
		
		return "User/view-userProfile";
	}
	
	//open to edit the contact details
	
	@GetMapping("/edit-contact/{id}")
	public String openEditContact(@PathVariable("id") Integer id, Model model) {
		
		Contact contact = this.contactRepository.findById(id).get();
		
		model.addAttribute("title", "Edit Contact Details");
		model.addAttribute("contact", contact);
		
		return "User/edit-contact";
	}
	
	//edit the contact details
	
	@PostMapping("/edit-contact/{id}")
	public String editContact(@PathVariable("id") Integer id, @Valid @ModelAttribute("contact") Contact contact, BindingResult result,
			Principal principal, Model model, HttpSession session, 
			@RequestParam("profileImage") MultipartFile image) {

		try {

			if (result.hasErrors()) {
				System.out.println(result);
				throw new Exception();

			} else {
				System.out.println(contact);

				String name = principal.getName();
				User user = this.userRepository.getUserByUserName(name);
				
				Contact oldContactDetail = this.contactRepository.findById(id).get();

				if (!image.isEmpty()) {
					
					contact.setImage(image.getOriginalFilename());

					String absolutePath = new ClassPathResource("static/image").getFile().getAbsolutePath();

					Path path = Paths.get(absolutePath + File.separator + image.getOriginalFilename());

					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					
				} else {
					System.out.println("Image is empty");
					contact.setImage(oldContactDetail.getImage());
				}
				
				contact.setUser(user);
				contact.setCid(id);

				// save the new contact
				contactRepository.save(contact);

				model.addAttribute("contact", new Contact());
				session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));

				return "User/add_contact_form";
			}

		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("contact", contact);
			session.setAttribute("message", new Message("Something went wrong!!", "alert-danger"));

			return "User/add_contact_form";
		}
	}
	
	@GetMapping("/delete/{cid}")
	public String deletecontact(@PathVariable("cid") Integer cid, HttpSession session,Principal principal) {
		
		Contact contact = this.contactRepository.findById(cid).get();
		
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		
		if(user.getUid()==contact.getUser().getUid()) {
			contact.setUser(null);
			this.contactRepository.delete(contact);
		}
		System.out.println("Deleted");
		session.setAttribute("message", new Message("Contact Deleted Successfully", "alert-success"));
		
		return "redirect:/user/view-Contacts/0";
	}
	
	@GetMapping("/settings")
	public String openSetting(Model model) {
		
		model.addAttribute("title", "Change Password");
		return "User/settings";
	}
	
	
	//change the user password
	
	@PostMapping("/password-change")
	public String changepassword(@RequestParam("oldPassword") String oldPassword, 
			@RequestParam("newPassword") String newPassword,
			@RequestParam("CnewPassword") String CnewPassword, 
			Principal principal, HttpSession session)
	{
		
		User user = this.userRepository.getUserByUserName(principal.getName());
		
		if(bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			if(newPassword.equals(CnewPassword)) {
				
				user.setPassword(bCryptPasswordEncoder.encode(newPassword));
				this.userRepository.save(user);
				session.setAttribute("message", new Message("Password changed Successfully", "alert-success"));
				
			}
			else
				session.setAttribute("message", new Message("New and confirm Password is not matched", "alert-danger"));
		}
		else 
			session.setAttribute("message", new Message("Old password is not matched", "alert-danger"));
		
		return "redirect:/user/settings";
	}
	
}
