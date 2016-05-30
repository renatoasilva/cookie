package com.cookie.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import com.cookie.model.User;
import com.cookie.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private UserService underTest;
	@Mock
	private User user;
	@Mock
	private User user2;
	@Mock
	private UserRepository userRepository;
	private static final String EMAIL = "e@a.com";
	private static final String ID = "1234";

	@Before
	public void setup() {
		underTest = new UserServiceImpl();
		ReflectionTestUtils.setField(underTest, "repository", userRepository);

		when(user.getEmail()).thenReturn(EMAIL);
		when(userRepository.findByEmail(EMAIL)).thenReturn(null);
		when(userRepository.save(user)).thenReturn(user);
		when(user.getEmail()).thenReturn(EMAIL);
		when(user2.getEmail()).thenReturn(EMAIL);
		when(userRepository.findById(ID)).thenReturn(user);
	}

	@Test
	public void testSaveUserSuccess() throws Exception {
		User actual = underTest.saveUser(user);
		assertEquals(user, actual);
		verify(userRepository).save(user);
		verify(userRepository).findByEmail(user.getEmail());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveUserNotFoundFail() throws Exception {
		when(user.getEmail()).thenReturn(EMAIL);
		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		underTest.saveUser(user);
	}

	@Test
	public void testUpdateUserSuccess() throws Exception {
		when(userRepository.save(user)).thenReturn(user2);
		User actual = underTest.updateUser(ID, user);
		assertEquals(user2, actual);
		verify(userRepository).save(user);
		verify(userRepository).findById(ID);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testUpdateUserIdNotFoundFail() throws Exception {
		when(userRepository.findById(ID)).thenReturn(null);
		underTest.updateUser(ID, user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testUpdateUserEmailNotMatchFail() throws Exception {
		when(user2.getEmail()).thenReturn(EMAIL + ".uk");
		when(userRepository.findById(ID)).thenReturn(user2);
		underTest.updateUser(ID, user);
	}

	@Test
	public void testFindUserByIdSuccess() throws Exception {
		User actual = underTest.findUserById(ID);
		assertEquals(user, actual);
		verify(userRepository).findById(ID);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testFindUserByIdFail() throws Exception {
		underTest.findUserById(ID + 1);
	}

	@Test
	public void testFindUserByEmailSuccess() throws Exception {
		when(userRepository.findByEmail(EMAIL)).thenReturn(user);
		User actual = underTest.findUserByEmail(EMAIL);
		assertEquals(user, actual);
		verify(userRepository).findByEmail(EMAIL);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testFindUserByEmailFail() throws Exception {
		underTest.findUserByEmail(EMAIL + 1);
	}

}
