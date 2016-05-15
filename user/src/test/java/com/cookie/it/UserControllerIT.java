package com.cookie.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.cookie.Util.Util;
import com.cookie.model.User;
import com.cookie.repository.UserRepository;

public class UserControllerIT extends BaseIT {
	private User user;
	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() {
		super.setup();
		user = Util.createDefaultUser();
	}

	@Test
	public void testFindUserByIdSuccess() throws Exception {
		User actual = createUser(user);
		actual = template.exchange(getBaseUrl() + "/users/v1.0/" + actual.getId(), HttpMethod.GET,
				new HttpEntity<>(user), User.class).getBody();
		assertNotNull(actual.getId());
		assertNull(user.getPassword(), actual.getPassword());
		assertEquals(user.getEmail(), actual.getEmail());
	}

	@Test
	public void testFindUserByIdNotFoundFail() throws Exception {
		Error actual = template.exchange(getBaseUrl() + "/users/v1.0/randomID", HttpMethod.GET,
				null, Error.class).getBody();
		assertNotNull(actual);
//		assertNull(, actual.getMessage());
//		assertEquals(user.getEmail(), actual.getEmail());
	}

	@Test
	public void testFindUserByEmailSuccess() throws Exception {
		User actual = createUser(user);
		actual = template.exchange(getBaseUrl() + "/users/v1.0?email=" + actual.getEmail(), HttpMethod.GET,
				null, User.class).getBody();
		assertNotNull(actual.getId());
		assertNull(user.getPassword(), actual.getPassword());
		assertEquals(user.getEmail(), actual.getEmail());
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
	public void testUpdateUserSuccess() throws Exception {
		User actual = createUser(user);
		user.setFirstName("FIRST_NAME_CHANGED");
		user.setLastName("LAST_NAME_CHANGED");
		user.setPassword("PASSWORD_CHANGED");
		actual = template.exchange(getBaseUrl() + "/users/v1.0/" + actual.getId(), HttpMethod.PUT,
				new HttpEntity<>(user), User.class).getBody();
		assertNotEquals(user, actual);

		// Asserting user in mongoDB
		assertTrue(userRepository.exists(actual.getId()));
		User dbUser = userRepository.findOne(actual.getId());
		assertEquals(user.getFirstName(), dbUser.getFirstName());
		assertEquals(user.getLastName(), dbUser.getLastName());
		assertEquals(user.getEmail(), dbUser.getEmail());
	}

	private User createUser(User user) {
		User actual = template.exchange(getBaseUrl() + "/users/v1.0/", HttpMethod.POST, new HttpEntity<>(user), User.class).getBody();
		assertNotNull(actual.getId());
		return actual;
	}
}
