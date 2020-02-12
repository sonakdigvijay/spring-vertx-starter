package com.learning;

import com.learning.verticle.ArticleRecipientVerticle;
import com.learning.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Configuration
public class SpringVertxStarterApplication {

	@Autowired
	private ServerVerticle serverVerticle;

	@Autowired
	private ArticleRecipientVerticle articleRecipientVerticle;

	public static void main(String[] args) {
		SpringApplication.run(SpringVertxStarterApplication.class, args);
	}

	@PostConstruct
	public void deployVerticle() {
		final Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(serverVerticle);
		vertx.deployVerticle(articleRecipientVerticle);
	}

}
