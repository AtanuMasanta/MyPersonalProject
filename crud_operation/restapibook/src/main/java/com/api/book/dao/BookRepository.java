package com.api.book.dao;

import org.springframework.data.repository.CrudRepository;
import com.api.book.entity.Book;
public interface BookRepository extends CrudRepository<Book, Integer> 
{
	public Book findById(int id);

}
