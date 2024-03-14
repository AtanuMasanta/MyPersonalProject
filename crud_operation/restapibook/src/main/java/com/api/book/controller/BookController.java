package com.api.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.book.entity.Book;
import java.util.*;
import com.api.book.services.bookservices;

@RestController 
public class BookController 
{
	@Autowired
	private bookservices ob;
	
	@GetMapping("/all-books")
	public ResponseEntity<List<Book>> getBooks()
	{
		List<Book>list= ob.getAllBooks();
		if(list.size()<=0)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	
		}
		return ResponseEntity.of(Optional.of(list));
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") int id)
	{
		Book book=ob.getBookById(id);
		if(book==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(book));
	}
	@PostMapping("/books")
	public Book AddBook(@RequestBody Book b)
	{
		Book b1=ob.AddBook(b);
		return b1;
		
	}
	

	@DeleteMapping("/books/{bookid}")
	public String deleteBook(@PathVariable("bookid") int bookid)
	{
		ob.DeleteBook(bookid);
		String str="deleted book id is";
		return str;
	}
	
	@PutMapping("/books/{bookid}")
	public Book updateBook(@RequestBody Book book,@PathVariable("bookid") int bookid)
	{
		ob.updatebook(book,bookid);
		return book;
	}
	
	
}
