package com.cookie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeathTestUserController {

	@RequestMapping(value = "/users/check", method = RequestMethod.GET)
	public String test() {
		return "Greetings from Cookie World!";
	}
}
