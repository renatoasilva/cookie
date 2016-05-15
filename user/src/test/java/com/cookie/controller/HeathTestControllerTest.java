package com.cookie.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HeathTestControllerTest {

	private HeathTestUserController instance;

	@Before
	public void setup() {
		instance = new HeathTestUserController();
	}

	@Test
	public void testHealthCheck() {
		assertEquals("Greetings from Cookie World!", instance.test());
	}
}
