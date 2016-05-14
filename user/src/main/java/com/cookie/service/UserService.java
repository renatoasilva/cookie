package com.cookie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cookie.model.User;
import com.cookie.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	private UserRepository repository;

	public User saveUser(User user) {
		// save a user
		return repository.save(user);
	}

	public User updateUser(User user) {
		return repository.save(repository.findByEmail(user.getEmail()));
	}
}
