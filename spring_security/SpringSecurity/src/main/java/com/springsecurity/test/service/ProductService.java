package com.springsecurity.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.springsecurity.test.entity.Product;
import com.springsecurity.test.entity.UserInfo;
import com.springsecurity.test.reository.UserRepository;

import java.util.*;
@Service
public class ProductService 
{
	    @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
		static List<Product>list=new ArrayList();
		static
		{
			list.add(new Product(1,"phone"));
			list.add(new Product(2,"watch"));
			list.add(new Product(3,"laptop"));
		}
		public static int addProduct(Product p)
		{
			list.add(p);
			return 1;
		}
		public static List<Product> getAllProduct()
		{
			return list;
		}
		public static Optional<Product> getProductById(int id)
		{
			Optional<Product>p;
			for(Product x:list)
			{
				if(x.getId()==id)
				{
					p=Optional.of(x);
					return p;
				}
			}
			return null;
		}
		public String addUser(UserInfo userInfo)
		{
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));;
			userRepository.save(userInfo);
			return "saed to db successfullyy ... !!";
		}

}
