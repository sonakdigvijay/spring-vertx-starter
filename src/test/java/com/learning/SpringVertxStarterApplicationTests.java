package com.learning;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class SpringVertxStarterApplicationTests {

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void givenUrl_whenReceivedArticles_thenSuccess() throws InterruptedException {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/vertx/test", String.class);

		assertEquals(200, responseEntity.getStatusCodeValue());
	}

}
