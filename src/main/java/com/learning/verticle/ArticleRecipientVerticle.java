package com.learning.verticle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.service.ArticleService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleRecipientVerticle extends AbstractVerticle {

    public static final String GET_ALL = "getAllArticles";

    @Autowired
    private ArticleService articleService;

    private final ObjectMapper mapper = Json.mapper;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<String>consumer(GET_ALL)
                .handler(getAllArticleService(articleService));
    }

    private Handler<Message<String>> getAllArticleService(ArticleService articleService) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                    future.complete(
                        mapper.writeValueAsString(articleService.getAllArticle())
                );
                } catch(JsonProcessingException e) {
                    future.fail(e);
                }
            }, result -> {
                    if(result.succeeded()) {
                        msg.reply(result.result());
                    } else {
                        msg.reply(result.cause().toString());
                    }
                }
        );
    }
}
