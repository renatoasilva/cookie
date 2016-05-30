package com.cookie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookie.exceptions.CustomDataIntegrityViolationException;
import com.cookie.model.Error;
import com.cookie.model.User;
import com.cookie.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	public User saveUser(User user) {
		// save a user
		if(repository.findByEmail(user.getEmail())==null){
			return repository.save(user);
		}else {
			throw new CustomDataIntegrityViolationException(Error.ERROR_CODE.USER_ALREADY_EXISTS, user.getEmail() + " already exists");
		}
	}

	public User updateUser(String id, User user) {
		User existingUser = repository.findById(id);
		if(existingUser!=null && existingUser.getEmail().equals(user.getEmail())){
			user.setId(existingUser.getId());
			return repository.save(user);
		}else {
			throw new CustomDataIntegrityViolationException(Error.ERROR_CODE.USER_NOT_FOUND, "User with id="+ id +" does not exist.");
		}
	}

	public User findUserById(String id) {
		User user = repository.findById(id);
		if(user==null){
			throw new CustomDataIntegrityViolationException(Error.ERROR_CODE.USER_NOT_FOUND, "User with id="+ id +" does not exist.");
		}
		return user;
	}

	public User findUserByEmail(String email) {
		User user = repository.findByEmail(email);
		if(user==null){
			throw new CustomDataIntegrityViolationException(Error.ERROR_CODE.USER_NOT_FOUND, "User with email="+ email +" does not exist.");
		}
		return user;
	}
}
