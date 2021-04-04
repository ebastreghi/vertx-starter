package com.vertx_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class WorkerExample extends AbstractVerticle {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    //this code is going to be executed in a worker thread
    vertx.executeBlocking(event -> {
      System.out.println("Executing blocking code");
      try {
        Thread.sleep(5000);
        event.complete();
        //event.fail("force fail");
      }catch (InterruptedException e){
        System.out.println("Failed: " + e.getMessage());
        event.fail(e);
      }
    }, result -> {
        if(result.succeeded()){
          System.out.println("Blocking call done");
        }else{
          System.out.println("Blocking call failed due to: " + result.cause());
        }
    });
  }
}
