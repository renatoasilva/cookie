package com.cookie.service;

import com.cookie.model.User;

public interface UserService {

	User saveUser(User user);

	User updateUser(String id, User user);

	User findUserById(String id);

	User findUserByEmail(String email);
}
