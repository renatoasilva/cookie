package com.cookie.controller;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cookie.model.User;
import com.cookie.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1.0/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User findUserById(@PathVariable(value="id") @NotBlank final String id){
		log.debug("Get user with id "+ id);
		return userService.findUserById(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public User findUserByEmail(@RequestParam(name="email") @NotBlank final String email){
		log.debug("Get user with email "+ email);
		return userService.findUserByEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public User saveUser(@RequestBody @Valid User user){
		log.debug("Save user "+ user);
		return userService.saveUser(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public User updateUser (@PathVariable(value="id") final String id, @RequestBody @Valid User user) {
		log.debug("Update user "+ user + "with id="+id);
		return userService.updateUser(id, user);
	}
}
