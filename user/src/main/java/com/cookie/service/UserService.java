package com.cookie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.cookie.model.User;
import com.cookie.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	private UserRepository repository;

	public User saveUser(User user) {
		// save a user
		if(repository.findByEmail(user.getEmail())==null){
			return repository.save(user);
		}else {
			throw new DataIntegrityViolationException(user.getEmail() + " already exists");
		}
	}

	public User updateUser(String id, User user) {
		User existingUser = repository.findById(id);
		if(existingUser!=null && existingUser.getEmail().equals(user.getEmail())){
			user.setId(existingUser.getId());
			return repository.save(user);
		}else {
			throw new DataIntegrityViolationException("User with id="+ id +" does not exist.");
		}
	}

	public User findUserById(String id) {
		User user = repository.findById(id);
		if(user==null){
			throw new DataIntegrityViolationException("User with id="+ id +" does not exist.");
		}
		return user;
	}

	public User findUserByEmail(String email) {
		User user = repository.findByEmail(email);
		if(user==null){
			throw new DataIntegrityViolationException("User with email="+ email +" does not exist.");
		}
		return user;
	}
}
