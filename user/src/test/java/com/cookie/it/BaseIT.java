package com.cookie.it;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.cookie.UserApplication;

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

//	protected RestTemplate restCustomTemplate() {
//		ObjectMapper mapper = Utils.getObjectMapper();
//		HALMessageConverter converter = new HALMessageConverter();
//		converter.setObjectMapper(mapper);
//
//		List<HttpMessageConverter<?>> converterList = new ArrayList<HttpMessageConverter<?>>();
//		converterList.add(converter);
//		TestRestTemplate testRestTemplate = new TestRestTemplate();
//		converterList.addAll(testRestTemplate.getMessageConverters());
//		RestTemplate restTemplate = new RestTemplate(converterList);
//		restTemplate.setErrorHandler(testRestTemplate.getErrorHandler());
//		return restTemplate;
//	}

//	/**
//	 * Utility class that gives the ability to support HATEOAS and convert "_links" into "links" for ResourceSupport objects
//	 */
//	private static class HALMessageConverter extends MappingJackson2HttpMessageConverter {
//
//	}

}
