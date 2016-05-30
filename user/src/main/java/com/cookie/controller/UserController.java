package com.cookie.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
		User user = userService.findUserById(id);
		Resource<User> resource = new Resource<User>(user);
		resource.add(linkTo(methodOn(this.getClass()).findUserById(id)).withSelfRel());
		return user;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Resource<User> findUserByEmail(@RequestParam(name="email") @NotBlank final String email){
		log.debug("Get user with email "+ email);
		final User user = userService.findUserByEmail(email);
		Resource<User> resource = new Resource<User>(user);
		resource.add(linkTo(methodOn(this.getClass()).findUserById(user.getId())).withSelfRel());
		return resource;
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	@ResponseStatus(code=HttpStatus.CREATED)
	public Resource<User> saveUser(@RequestBody @Valid User user){
		log.debug("Save user "+ user);
		User newUser = userService.saveUser(user);
		Resource<User> resource = new Resource<User>(newUser);
		resource.add(linkTo(methodOn(this.getClass()).saveUser(user)).withSelfRel());
		resource.add(linkTo(methodOn(this.getClass()).findUserById(user.getId())).withRel("findById"));
		resource.add(linkTo(methodOn(this.getClass()).findUserByEmail(user.getEmail())).withRel("findByEmail"));
		resource.add(linkTo(methodOn(this.getClass()).updateUser(user.getId(), user)).withRel("update"));
		return resource;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(code=HttpStatus.ACCEPTED)
	public Resource<User> updateUser (@PathVariable(value="id") final String id, @RequestBody @Valid User user) {
		log.debug("Update user "+ user + "with id="+id);
		final User updatedUser = userService.updateUser(id, user);
		Resource<User> resource = new Resource<User>(updatedUser);
		resource.add(linkTo(methodOn(this.getClass()).updateUser(user.getId(), user)).withSelfRel());
		return resource;
	}
}
