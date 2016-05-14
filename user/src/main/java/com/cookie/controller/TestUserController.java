package com.cookie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/test")
public class TestUserController {

	@RequestMapping(value="/")
	public String test() {
		return "Greetings from Cookie World!";
	}
}
