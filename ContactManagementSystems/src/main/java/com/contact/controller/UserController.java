package com.contact.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.entity.Contact;
import com.contact.entity.User;
import com.contact.helper.ChangePassword;
import com.contact.helper.Message;
import com.contact.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController 
{
	
	@Autowired
	private UserService userService;
	
	//showing user dashboard
	@RequestMapping("/index")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String dashboard(Model model,Principal principal)
	{
		String username=principal.getName();
		model.addAttribute("title","User DashBoard");
		User user=this.userService.getUserByName(username);
	//	System.out.print("normal/user :"+user);
		model.addAttribute("user",user);
		return "normal/user_dashboard.html";
	}
	//open add contact form method
	@RequestMapping("/add-contact")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String openAddContactForm(Model model,Principal principal)
	{
		model.addAttribute("title","Add Contact");
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
		model.addAttribute("user",user);
		model.addAttribute("contact",new Contact());
		return "normal/add_contact_form";
	}
	
	//process add contact form
	@RequestMapping(value="/process-contact",method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String processContact(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,
			Model model,Principal principal,HttpSession session)
	{
			model.addAttribute("title","Add Contact");
			String username=principal.getName();
			User user=this.userService.getUserByName(username);
			boolean ans=this.userService.AddContactFormtoDb(user, contact, file);
			model.addAttribute("user",user);
			if(ans)
			{
				session.setAttribute("message",new Message("your contact is added !!","success"));
			}
			else
			{
				session.setAttribute("message",new Message("something went wrong ,try again !!","danger"));
			}
			return "normal/add_contact_form";
	}
	
	@RequestMapping("/show-contact/{page}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String showContacts(@PathVariable("page")int page,Model model,Principal principal)
	{
		model.addAttribute("title","your contacts");
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
		model.addAttribute("user",user);
		
		Page<Contact>contacts=this.userService.showAllContact(user, page);
		if(contacts.isEmpty())
		{
			return "normal/noContact";
		}
		model.addAttribute("contacts",contacts);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalpages", contacts.getTotalPages());
		return "normal/show_contact";
		
	}
	
	//showing all the details of a contact .
	@RequestMapping("/contact/{cid}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String showContactDetails(@PathVariable("cid") int cid,Model model,Principal principal)
	{
		model.addAttribute("title","About a contact");
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
		model.addAttribute("user",user);
		
		boolean ans=userService.DetailsOf_a_Contact(user,cid);
		if(ans)
		{
			Contact contact=this.userService.getContactById(cid);
			model.addAttribute("contact",contact);
			return "normal/contact_details";
		}
		else
		{
			return "normal/error";
		}
		
	}
	@RequestMapping("/delete/{cid}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String deleteContactHandler(@PathVariable("cid") int cid,Principal principal,HttpSession session) throws IOException
	{
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
	    boolean ans=this.userService.deleteContact(user, cid);
	    if(ans)
	    {
		   session.setAttribute("message",new Message("your contact is deleted ..!","success"));
		   return "redirect:/user/show-contact/0";
	    }
	    else
	    {
		   return "normal/error";
	    }
	}
	
	//update form handler
	@PostMapping("/update-contact/{cid}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String update_form(@PathVariable("cid") int cid,Principal principal,Model model)
	{
		model.addAttribute("title","Update-contact .");
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
		model.addAttribute("user",user);
		Optional<Contact> contact_optional=this.userService.updateContactForm(cid);
		if(contact_optional.isEmpty())
		{
			return "normal/error";
		}
		Contact contact=contact_optional.get();
		model.addAttribute("contact",contact);
		return "normal/update_form";
	}
	
	@PostMapping("/update-contact")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String updateHandler(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,
			Principal principal,Model model,HttpSession session)
	{
		
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
		model.addAttribute("user",user);
		String str=this.userService.HandleUpdateContactForm(file, user, contact);
		if(str.equals("null"))
		{
			session.setAttribute("message",new Message("some problem to process your data, please try again ..!","danger"));
			return "normal/error";
		}
		else if(str.equals("done"))
		{
			session.setAttribute("message",new Message("your contact is updated ..!","success"));
		}
		else if(str.equals("error"))
		{
			session.setAttribute("message",new Message("some problem to process your data, please try again ..!","danger"));
			return "normal/error";
		}
		return "redirect:/user/contact/"+contact.getcId();
	}
	
	
	//your profile
	@RequestMapping("/profile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String yourProfile(Principal principal,Model model)
	{
		model.addAttribute("title","Your Profile ...");
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
		model.addAttribute("user",user);
		return "normal/profile";
	}
	
	@PostMapping("/update-user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String updateUserform(Principal principal,Model model)
	{
		model.addAttribute("title","update user");
		String name=principal.getName();
		User user=this.userService.getUserByName(name);
		model.addAttribute("user",user);
		return "normal/update-user";
	}
	
	@PostMapping("/update-user/process")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String updateUser(@ModelAttribute User reciveuser,Principal principal,Model model,HttpSession session)
	{
		String name=principal.getName();
		User userfromdb=this.userService.getUserByName(name);
		int val=this.userService.userFormHandling(reciveuser, userfromdb);
		if(val==1)
		{
			session.setAttribute("message",new Message("your information is updated ..!","success"));
		}
		else
		{
			session.setAttribute("message",new Message("some problem to process your data, please try again ..!","danger"));
		}
		return "redirect:/user/profile";
		
	}
	
	@RequestMapping("/change-password")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String changepasswordForm(Principal principal,Model model)
	{
		model.addAttribute("title","Change Password");
		String username=principal.getName();
		User user=this.userService.getUserByName(username);
		model.addAttribute("user",user);
		model.addAttribute("changepassword",new ChangePassword());
		return "normal/changePassword";
	}
	
	
	@RequestMapping(value="/process-change-password",method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String processChangepasswordform(@ModelAttribute ChangePassword changepassword,Principal principal,HttpSession session)
	{
		//System.out.print("change password form :"+changepassword);
		//return "redirect:/user/profile";
		String name=principal.getName();
		User user=userService.getUserByName(name);
		String str=userService.ChangeUserPassword(changepassword,user);
		if(str.equals("success"))
		{
			session.setAttribute("message",new Message("your password is updated ..!","success"));
		}
		else if(str.equals("not_matching"))
		{
			session.setAttribute("message",new Message("old password is incorrect","danger"));
			return "redirect:/user/change-password";
		}
		else if(str.equals("mismatch"))
		{
			session.setAttribute("message",new Message("new password & confirm new password are not matching","danger"));
			return "redirect:/user/change-password";
		}
		
		return "redirect:/user/index";

	}
}
