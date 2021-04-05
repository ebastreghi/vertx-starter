package com.vertx_starter.eventbus;

import com.vertx_starter.codec.LocalMessageCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PingPongExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle());
    vertx.deployVerticle(new PongVerticle());
  }

  static class PingVerticle extends AbstractVerticle{

    public static final String MY_REQUEST_ADDRESS = PingVerticle.class.getName();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      var eventBus = vertx.eventBus();
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      final Ping ping = new Ping("Hello", true);
      System.out.println("Sending " + ping);
      //the eventBus is used to communicate verticles with each others
      eventBus.<Pong>request(MY_REQUEST_ADDRESS, ping, reply -> {
        System.out.println("Response " + reply.result().body());
      });
      startPromise.complete();
    }
  }

  static class PongVerticle extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      var eventBus = vertx.eventBus();
      eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      eventBus.<Ping>consumer(PingVerticle.MY_REQUEST_ADDRESS, message -> {
        System.out.println("Received message " + message.body());
        message.reply(new Pong(0));
      });
      startPromise.complete();
    }
  }

}
