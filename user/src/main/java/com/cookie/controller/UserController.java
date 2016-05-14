package com.cookie.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cookie.model.User;
import com.cookie.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public User saveUser(@RequestBody @Valid User user) {
		return userService.saveUser(user);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public User updateUser(@RequestBody @Valid User user) {
		return userService.updateUser(user);
	}
}
