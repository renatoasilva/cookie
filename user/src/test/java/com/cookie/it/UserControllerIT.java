package com.cookie.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.cookie.Util.Util;
import com.cookie.model.Error;
import com.cookie.model.Error.ERROR_CODE;
import com.cookie.model.User;
import com.cookie.repository.UserRepository;

public class UserControllerIT extends BaseIT {
	private User user;
	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() {
		template = restTemplate();
		user = Util.createDefaultUser();
	}

	@Test
	public void testFindUserByIdSuccess() throws Exception {
		User actual = createUser(user);
		actual = template.exchange(getURL("/" + actual.getId()), HttpMethod.GET, new HttpEntity<>(user), User.class).getBody();
		assertNotNull(actual.getId());
		assertNull(user.getPassword(), actual.getPassword());
		assertEquals(user.getEmail(), actual.getEmail());
	}

	@Test
	public void testFindUserByIdNotFoundFail() throws Exception {
		Error actual = template.exchange(getURL("/randomID"), HttpMethod.GET, null, Error.class).getBody();
		assertNotNull(actual);
		assertEquals(ERROR_CODE.USER_NOT_FOUND, actual.getCode());
		assertEquals("User with id=randomID does not exist.", actual.getMessage());
	}

	@Test
	public void testFindUserByEmailSuccess() throws Exception {
		User actual = createUser(user);
		actual = template.exchange(getURL("?email=" + actual.getEmail()), HttpMethod.GET, null, User.class).getBody();
		assertNotNull(actual.getId());
		assertNull(user.getPassword(), actual.getPassword());
		assertEquals(user.getEmail(), actual.getEmail());
	}

	@Test
	public void testFindUserByEmailFail() throws Exception {
		String invalidEmail = "e@invalidemail.com";
		Error error = template.exchange(getURL("?email=" + invalidEmail), HttpMethod.GET, null, Error.class).getBody();
		assertEquals(ERROR_CODE.USER_NOT_FOUND, error.getCode());
		assertEquals("User with email=" + invalidEmail + " does not exist.", error.getMessage());
	}

	@Test
	public void testSaveUserSuccess() throws Exception {
		User actual = createUser(user);
		assertNotNull(actual.getId());
		assertNotNull(user.getPassword(), actual.getPassword());
		assertEquals(user.getEmail(), actual.getEmail());

		// Asserting user in mongoDB
		assertTrue(userRepository.exists(actual.getId()));
		User dbUser = userRepository.findOne(actual.getId());
		assertEquals(user.getFirstName(), dbUser.getFirstName());
		assertEquals(user.getLastName(), dbUser.getLastName());
		assertEquals(user.getEmail(), dbUser.getEmail());
	}

	@Test
	public void testSaveUserAlreadyExistsFail() throws Exception {
		User actual = createUser(user);
		Error error = template.exchange(getURL(), HttpMethod.POST, new HttpEntity<>(user), Error.class).getBody();

		// Asserting user in mongoDB
		assertTrue(userRepository.exists(actual.getId()));
		assertEquals(ERROR_CODE.USER_ALREADY_EXISTS, error.getCode());
		assertEquals(user.getEmail() + " already exists", error.getMessage());
	}

	@Test
	public void testUpdateUserSuccess() throws Exception {
		User actual = createUser(user);
		user.setFirstName("FIRST_NAME_CHANGED");
		user.setLastName("LAST_NAME_CHANGED");
		user.setPassword("PASSWORD_CHANGED");
		actual = template.exchange(getURL("/" + actual.getId()), HttpMethod.PUT, new HttpEntity<>(user), User.class)
				.getBody();
		assertNotEquals(user, actual);

		// Asserting user in mongoDB
		assertTrue(userRepository.exists(actual.getId()));
		User dbUser = userRepository.findOne(actual.getId());
		assertEquals(user.getFirstName(), dbUser.getFirstName());
		assertEquals(user.getLastName(), dbUser.getLastName());
		assertEquals(user.getEmail(), dbUser.getEmail());
	}

	@Test
	public void testUpdateUserNotFoundFail() throws Exception {
		User actual = new User();
		actual.setId("INVALID_ID");
		user.setFirstName("FIRST_NAME_CHANGED");
		user.setLastName("LAST_NAME_CHANGED");
		user.setPassword("PASSWORD_CHANGED");
		Error error = template.exchange(getURL("/" + actual.getId()), HttpMethod.PUT, new HttpEntity<>(user), Error.class).getBody();

		// Asserting user in mongoDB
		assertFalse(userRepository.exists(actual.getId()));
		assertEquals(ERROR_CODE.USER_NOT_FOUND, error.getCode());
		assertEquals("User with id=INVALID_ID does not exist.", error.getMessage());
	}

	private User createUser(User user) {
		Resource<User> actual = template.exchange(getURL(), HttpMethod.POST, new HttpEntity<User>(user), new ParameterizedTypeReference<Resource<User>>(){}).getBody();
		assertNotNull(actual.getContent().getId());
		assertTrue(actual.hasLinks());
		return actual.getContent();
	}

	private String getURL() {
		return getURL(null);
	}

	private String getURL(String path) {
		return getBaseUrl() + "/v1.0/users" + (path != null ? path : "");
	}
}
