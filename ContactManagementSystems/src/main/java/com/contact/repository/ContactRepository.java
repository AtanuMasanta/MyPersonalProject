package com.contact.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.contact.entity.Contact;
public interface ContactRepository extends JpaRepository<Contact,Integer>
{
	@Query("from Contact as c where c.user.id=:userid")
	public Page<Contact> findContactsByUser(@Param("userid")int userid,Pageable pageable);
}
