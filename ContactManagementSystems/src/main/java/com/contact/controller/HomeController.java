package com.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.entity.User;
import com.contact.helper.Message;
import com.contact.service.HomeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contact")
public class HomeController
{
	@Autowired
	private HomeService homeService;
	
	@RequestMapping("/home")
	public String home(Model model)
	{
		model.addAttribute("title","Home - Contact management system");
		return "home.html";
	}
	
	
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About - Contact management system");
		return "about.html";
	}
	
	
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title","Register - Contact management system");
		model.addAttribute("user",new User());
		return "signup.html";
	}
	
	@PostMapping("/do_register")
	public String signup(@Validated @ModelAttribute("user") User user,BindingResult result,@RequestParam(value="agreement",defaultValue ="false")
	boolean agreement,Model model,HttpSession session)
	{
		try
		{
			//System.out.print("result has error or not --"+result.hasErrors());
			//System.out.print("received user :"+user);
			if(!agreement)
			{
				//System.out.print("you have not accepted term and condition ...");
				throw new Exception("you have not aggred terms and condition ....");
				
			}
			if(result.hasErrors())
			{
				model.addAttribute("user",user);
				System.out.print("error has occured");
				return "signup.html";
			}
			
			//User res=this.homeService.AddUsertodatabase(user);
			this.homeService.AddUsertodatabase(user);
			//System.out.print(res);
			model.addAttribute("user",new User());
			session.setAttribute("message",new Message("Successfully Registered , now do login with your credential !!","alert-success"));
			return "signup.html";
		} 
		catch (Exception e)
		{
			//e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message",new Message("Something went wrong  "+e.getMessage(),"alert-danger"));
			return "signup.html";
		}
	
	}
	@GetMapping("/login")
	public String customLogin(Model model)
	{
		model.addAttribute("title","Login - Contact management system");
		return "login";
	}
	@GetMapping("/fail-url")
	public String loginfail()
	{
		return "normal/fail";
	}
	
	
	
}
