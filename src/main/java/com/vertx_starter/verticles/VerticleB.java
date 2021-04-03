package com.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleB extends AbstractVerticle {

  @Override
  public void start(final Promise<Void> promise) throws Exception {
    System.out.println("Start " + getClass().getName());
    promise.complete();
  }

}
