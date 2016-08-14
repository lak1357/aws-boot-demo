package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.Token;
import com.example.jwt.TokenGenerator;
import com.example.pojo.Response;
import com.example.pojo.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

@RestController
public class UserController 
{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping(method=RequestMethod.GET, path = "/users")
	public ResponseEntity<Response> getUsers()
	{
		Iterable<User> users = userRepository.findAll();
		Response r = new Response(true, users);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, path = "/login")
	public ResponseEntity<Response> login(@RequestBody User user)
	{
		if(user.getUsername() == null || user.getPassword() == null)
		{
			return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
		}
		
		User u = userRepository.findByUsername(user.getUsername());
		
		if(u == null || !user.getPassword().equals(u.getPassword()))
		{
			return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
		}
		else
		{
			Token token = TokenGenerator.createToken(u.getUsername());
			Response r = new Response(true, token);
			return new ResponseEntity<Response>(r,HttpStatus.OK);
		}
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST, path = "/verify")
	public ResponseEntity<Response> verify(@RequestBody Token token)
	{
		if(token.getToken() == null)
		{
			return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
		}
		if(TokenGenerator.verify(token.getToken()))
		{
			return new ResponseEntity<Response>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
		}
		
	}
	

	
	@RequestMapping(method=RequestMethod.POST, path = "/user")
	public ResponseEntity<Response> saveUser(@RequestBody User user)
	{
		if(userRepository.findByUsername(user.getUsername()) != null)
		{
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
		
		roleRepository.save(user.getRoles());
		User u = userRepository.save(user);
		
		Response r = new Response(true, u);
		return new ResponseEntity<Response>(r,HttpStatus.CREATED);
	}
	
	
}
