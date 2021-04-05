package com.vertx_starter;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {

  //with these two parameters we can access the vertx functionality
  @Test
  void promise_success(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      System.out.println("Success");
      //because we are in a unit test we have to complete the context
      context.completeNow();
    });
    System.out.println("End");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(5000, id -> {
      promise.complete("Success");
      System.out.println("Timer done");
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(result -> {
      System.out.println("Result: " + result);
      context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_map(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(5000, id -> {
      promise.complete("Success");
      System.out.println("Timer done");
    });
    final Future<String> future = promise.future();
    future
      .map(asString -> {
        System.out.println("Map String to JsonObject");
        return new JsonObject().put("key", asString);
      })
      .map(jsonObject -> new JsonArray().add(jsonObject))
      .onSuccess(result -> {
        System.out.println("Result: " + result);
        context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext context){
    vertx.createHttpServer()
      .requestHandler(request -> System.out.println(request))
      .listen(10000)
      .compose(server -> {
        System.out.println("Another task");
        return Future.succeededFuture(server);
      })
      .compose(server -> {
        System.out.println("Even more");
        return Future.succeededFuture(server);
      })
      .onFailure(context::failNow)
      .onSuccess(server -> {
        System.out.println("Server started on port " + server.actualPort());
      });
  }

}
