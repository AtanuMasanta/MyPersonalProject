package com.springsecurity.test.reository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springsecurity.test.entity.UserInfo;

public interface UserRepository extends CrudRepository<UserInfo,Integer>
{

	Optional<UserInfo> findByEmail(String email);
	
}
