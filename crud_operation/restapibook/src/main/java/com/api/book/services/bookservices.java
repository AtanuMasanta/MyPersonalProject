package com.api.book.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import com.api.book.entity.Book;
import com.api.book.dao.BookRepository;

@Component
public class bookservices 
{	
	@Autowired
	private BookRepository bookrepository; 
	//get all book from databases
	public List<Book> getAllBooks()
	{
		List<Book>list=(List<Book>)this.bookrepository.findAll();
		return list;
	}
	//get single book
	public Book getBookById(int id)
	{
		Book book=null;
		try
		{
			book=this.bookrepository.findById(id);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return book;
	}
	//adding book
	
	public Book AddBook(Book b)
	{
		Book result=bookrepository.save(b);
		return result;
	}
	//deleting the book
	public void DeleteBook(int bid)
	{
		bookrepository.deleteById(bid);
	}
	//update the book
	public void updatebook(Book book,int bookid)
	{
		book.setId(bookid);
		bookrepository.save(book);
	}
}
