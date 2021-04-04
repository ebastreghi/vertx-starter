package com.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.time.Duration;

public class PublishSubscribeExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new Subscribe1());
    vertx.deployVerticle(Subscribe2.class.getName(), new DeploymentOptions().setInstances(2));
  }

  static class Publish extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id -> {
        vertx.eventBus().publish(Publish.class.getName(), "Publish a message for everyone");
      });
    }
  }

  static class Subscribe1 extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
        System.out.println("Received1 " + message);
      });
    }
  }

  static class Subscribe2 extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
        System.out.println("Received2 " + message);
      });
    }
  }

}
