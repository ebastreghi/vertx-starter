package com.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PointToPointExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }

  static class Sender extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      //send a message each 1s
      vertx.setPeriodic(1000, id -> {
        vertx.eventBus().send(Sender.class.getName(), "Sending a message without waiting for response");
        System.out.println("Sending a message without waiting for response");
      });
    }
  }

  static class Receiver extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Sender.class.getName(), message -> {
        System.out.println("Received the message: " + message.body());
      });
    }
  }

}
