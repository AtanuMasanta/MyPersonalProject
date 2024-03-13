package com.contact.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contact.entity.Contact;
import com.contact.entity.User;
import com.contact.helper.ChangePassword;
import com.contact.repository.ContactRepository;
import com.contact.repository.UserRepository;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private ContactRepository contactRepository;
	
	public User getUserByName(String name) 
	{
		
		User u=this.userRepository.getUserByUserName(name);
		return u;
	}
	
	public Contact getContactById(int cid)
	{
		Optional<Contact> contact_optional=this.contactRepository.findById(cid);
		Contact contact=contact_optional.get();
		return contact;
		
	}
	
	
	public boolean AddContactFormtoDb(User user,Contact contact,MultipartFile file)
	{
		try
		{
			if(!file.isEmpty())
			{
				contact.setImage(file.getOriginalFilename());
				File saveFile=new ClassPathResource("static/img").getFile();
				
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
				
				System.out.print("image is uploaded");
			}
			else
			{
				contact.setImage("contact11.jpg");
			}
			contact.setUser(user);
			user.getContacts().add(contact);
			this.userRepository.save(user);
			System.out.print("added to database");
			return true;
		}
		catch(Exception e)
		{
			System.out.print("error :"+e.getMessage());
			return false;
		}
	}
	public boolean DetailsOf_a_Contact(User user,int cid)
	{
		Optional<Contact>contact_optional=this.contactRepository.findById(cid);
		
		if(contact_optional.isEmpty())
		{
			return false;
		}
		Contact contact=contact_optional.get();
		if(user.getId()==contact.getUser().getId())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	//show all contact with pagination .
	public Page<Contact> showAllContact(User user,int page)
	{
		Pageable pageable=PageRequest.of(page,3);
		Page<Contact>contacts=this.contactRepository.findContactsByUser(user.getId(),pageable);
		return contacts;
	}
	//delete contact
	public boolean deleteContact(User user,int cid)
	{
		try
		{
			Optional<Contact>contact_optional=this.contactRepository.findById(cid);
			if(contact_optional.isEmpty())
			{
				return false;
			}
			Contact contact=contact_optional.get();
			if(user.getId()==contact.getUser().getId())
			{
				contact.setUser(null);
				if(!contact.getImage().equals("contact11.jpg"))
				{
					File deleteFile=new ClassPathResource("static/img").getFile();
					File file1=new File(deleteFile,contact.getImage());
					file1.delete();
					System.out.print("image delted !!");
				}
				this.contactRepository.delete(contact);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	//update contact form
	public Optional<Contact> updateContactForm(int cid)
	{
		Optional<Contact> contact_optional=this.contactRepository.findById(cid);
		return contact_optional;
	}
	//handle update contact form data
	
	public String HandleUpdateContactForm(MultipartFile file,User user,Contact contact)
	{
		try
		{
			Optional<Contact> oldcontactoptional=this.contactRepository.findById(contact.getcId());
			if(oldcontactoptional.isEmpty())
			{
				return "null";
			}
			Contact oldcontact=oldcontactoptional.get();
			if(!file.isEmpty())
			{
				//file work
				//delete old image
				String imageName=oldcontact.getImage();
				if(!imageName.equals("contact11.jpg"))
				{
					File deleteFile=new ClassPathResource("static/img").getFile();
					File file1=new File(deleteFile,oldcontact.getImage());
					file1.delete();
				}
				
				
				//adding new image
				contact.setImage(file.getOriginalFilename());
				File saveFile=new ClassPathResource("static/img").getFile();
				
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
				
			}
			else
			{
				contact.setImage(oldcontact.getImage());
			}
			contact.setUser(user);
			this.contactRepository.save(contact);
			return "done";

		}
		catch(Exception e)
		{
		  //	e.printStackTrace();
			return "error";
		}
	}
	
	
	
	//update user data
	public int userFormHandling(User receivedUser,User userfromDb)
	{
		try
		{
			userfromDb.setName(receivedUser.getName());
			userfromDb.setEmail(receivedUser.getEmail());
			userfromDb.setAbout(receivedUser.getAbout());
			this.userRepository.save(userfromDb);
			return 1;
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return 0;
		}
	}
	
	//change userpassword
	
	public String ChangeUserPassword(ChangePassword changePassword,User user)
	{
		if(encoder.matches(changePassword.getOldpass(),user.getPassword()))
		{
			String newpass=changePassword.getNewpass();
			String confirm_new_pass=changePassword.getRetypenewpass();
			if(newpass.equals(confirm_new_pass))
			{
				user.setPassword(encoder.encode(newpass));
				this.userRepository.save(user);
				return "success";
			}
			else
			{
				return "mismatch";
			}
		}
		else
		{
			return "not_matching";
		}
	}
}
