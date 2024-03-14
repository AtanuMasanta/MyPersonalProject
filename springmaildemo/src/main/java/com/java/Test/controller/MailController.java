package com.java.Test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.Test.ServicePackage.MailService;
import com.java.Test.model.MailStructure;

@RestController
@RequestMapping("/mail")
public class MailController 
{
	@Autowired
	private MailService mailService;
	@PostMapping("/send/{mail}")
	public String sendmail(@PathVariable String mail,@RequestBody MailStructure mailStructure)
	{
		try
		{
			mailService.sendMail(mail, mailStructure);
			return "succesfully mail is sent !!";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "mail is not sent !!";
		
		
	}
}
