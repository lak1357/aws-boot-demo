package com.example.repository;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.pojo.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>
{
	
	public User findByName(String name);

	public User findByUsername(String username);
	
}
