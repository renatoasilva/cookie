package com.cookie.it;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.cookie.UserApplication;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(UserApplication.class)
@WebIntegrationTest("server.port:0")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public abstract class BaseIT {

	protected RestTemplate template;

	@Value("${local.server.port}")
	private int port;

	@Before
	public void setup() {
		template = new TestRestTemplate();
	}

	protected String getBaseUrl() {
		return "http://localhost:" + port;
	}

	protected RestTemplate restTemplate() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jackson2HalModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);
		List<HttpMessageConverter<?>> converterList = new ArrayList<HttpMessageConverter<?>>();
		converterList.add(converter);
		RestTemplate restTemplate = new RestTemplate(converterList);
		restTemplate.setErrorHandler(new TestRestTemplate().getErrorHandler());
		return restTemplate;
	}

}
