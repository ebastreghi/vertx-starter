package com.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class RequestResponseExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends AbstractVerticle{

    public static final String MY_REQUEST_ADDRESS = "my.request.address";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final String message = "Hello world";
      System.out.println("Sending " + message);
      //the eventBus is used to communicate verticles with each others
      eventBus.<String>request(MY_REQUEST_ADDRESS, message, reply -> {
        System.out.println("Response " + reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(RequestVerticle.MY_REQUEST_ADDRESS, message -> {
        System.out.println("Received message " + message.body());
        message.reply("Received your message. Thanks!");
      });
    }
  }

}
