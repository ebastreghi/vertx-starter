package com.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {

  @Override
  public void start(final Promise<Void> promise) throws Exception {
    System.out.println("Start " + getClass().getName() + " on thread " + Thread.currentThread().getName() + " with config " + config().toString());
    promise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    System.out.println("Stop " + getClass().getName());
    stopPromise.complete();
  }
}
