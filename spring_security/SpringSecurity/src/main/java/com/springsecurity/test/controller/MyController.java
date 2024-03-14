package com.springsecurity.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.springsecurity.test.dto.AuthRequest;
import com.springsecurity.test.entity.Product;
import com.springsecurity.test.entity.UserInfo;
import com.springsecurity.test.service.JwtService;
import com.springsecurity.test.service.ProductService;

@RestController
@RequestMapping("/product")
public class MyController 
{
	@Autowired
	ProductService productService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/welcome")
	public String welcome()
	{
		return "welcome to my application";
	}
	@PostMapping("/addUser")
	public String addnewUser(@RequestBody UserInfo userInfo)
	{
		
		String str=productService.addUser(userInfo);
		return str;
	}
	
	
	
	@PostMapping("/add")
	public String addProduct(Product p)
	{
		int x=productService.addProduct(p);
		if(x==1)
		{
			return "product is added successfully ....!";
		}
		return "product is not added successfully ....!";
	}
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Product> getAll()
	{
		List<Product>productList=productService.getAllProduct();
		return productList;
	}
	@GetMapping("/get-product/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Product getProductById(@PathVariable("id") int id)
	{
		Product p=productService.getProductById(id).get();
		return p;
	}
	
	@PostMapping("/authenticate")
	public String authenticateAndgetToken(@RequestBody AuthRequest authRequest)
	{
		Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		if(authenticate.isAuthenticated())
		{
			String str=jwtService.generateToken(authRequest.getEmail());
			return str;
		}
		else
		{
			throw new UsernameNotFoundException("invalid user request");
		}
		
	}
}
