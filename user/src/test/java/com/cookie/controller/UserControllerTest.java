package com.cookie.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.hateoas.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import com.cookie.model.User;
import com.cookie.service.UserService;

public class UserControllerTest {
	private UserController underTest;
	@Mock
	private UserService userService;
	@Spy
	private User user;
	private static final String EMAIL = "e@a.com";
	private static final String ID = "1234";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		underTest = new UserController();
		ReflectionTestUtils.setField(underTest, "userService", userService);

		Mockito.when(userService.updateUser(ID, user)).thenReturn(user);
		Mockito.when(userService.findUserByEmail(EMAIL)).thenReturn(user);
		Mockito.when(userService.findUserById(ID)).thenReturn(user);
		Mockito.when(userService.saveUser(user)).thenReturn(user);
		Mockito.when(user.getId()).thenReturn(ID);
		Mockito.when(user.getEmail()).thenReturn(EMAIL);
	}

	@Test
	public void testFindUserByIdSuccess() throws Exception {
		User actual = underTest.findUserById(ID);
		assertEquals(user, actual);
		verify(userService).findUserById(ID);
	}

	@Test
	public void testFindUserByEmailSuccess() throws Exception {
		Resource<User> actual = underTest.findUserByEmail(EMAIL);
		assertEquals(user, actual.getContent());
		verify(userService).findUserByEmail(EMAIL);
	}

	@Test
	public void testSaveUserSuccess() throws Exception {
		Resource<User> actual = underTest.saveUser(user);
		assertEquals(user, actual);
		verify(userService).saveUser(user);
	}

	@Test
	public void testUpdateUserSuccess() throws Exception {
		Resource<User> actual = underTest.updateUser(ID, user);
		assertEquals(user, actual.getContent());
		verify(userService).updateUser(ID, user);
	}

}
