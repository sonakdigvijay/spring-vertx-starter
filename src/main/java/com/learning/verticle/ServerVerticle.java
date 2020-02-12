package com.learning.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();
        Router router = Router.router(vertx);
        router.get("/vertx/test")
                .handler(this::getAllArticlesHandler);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8080));
    }

    private void getAllArticlesHandler(RoutingContext routingContext) {
        vertx.eventBus().<String>send(ArticleRecipientVerticle.GET_ALL, "", result ->{
           if (result.succeeded()) {
               routingContext.response()
                       .putHeader("content-type", "application/json")
                       .setStatusCode(200)
                       .end(result.result().body());
           } else {
               routingContext.response()
                       .setStatusCode(500)
                       .end();
           }
        });
    }
}
