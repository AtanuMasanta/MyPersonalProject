package com.java.Test.ServicePackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.java.Test.model.MailStructure;

@Service
public class MailService 
{
	@Autowired
	private JavaMailSender mailSender;
	public void sendMail(String mail,MailStructure mailStructure)
	{
		try
		{
			SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
			simpleMailMessage.setFrom("atanumasanta05@gmail.com");
			simpleMailMessage.setSubject(mailStructure.getSubject());
			simpleMailMessage.setText(mailStructure.getMessage());
			simpleMailMessage.setTo(mail);
			mailSender.send(simpleMailMessage);		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
