package com.cookie.Util;

import com.cookie.model.User;

public class Util {

	public static User createDefaultUser(){
		User user = new User();
		user.setFirstName("FIRST");
		user.setLastName("LAST");
		user.setEmail("email"+System.currentTimeMillis()+"@me.com");
		user.setPassword("password");
		return user;
	}
}
