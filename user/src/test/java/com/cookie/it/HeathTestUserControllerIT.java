package com.cookie.it;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HeathTestUserControllerIT extends BaseIT{

	@Test
	public void testTest() {
		String response = template.getForObject(getBaseUrl() + "users/check", String.class);
		assertEquals("Greetings from Cookie World!", response);
	}
}
