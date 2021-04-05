package com.vertx_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class WorkerExample extends AbstractVerticle {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //deplying a verticle as a worker to execute blocking I/O
    vertx.deployVerticle(new WorkerVerticle(),
      new DeploymentOptions()
      .setWorker(true)
        //Set the maximum number of worker threads to be used by the Vert.x instance.
      .setWorkerPoolSize(1)
      .setWorkerPoolName("my-worker-verticle")
      );

    startPromise.complete();
    executingBlockingCode();
  }

  private void executingBlockingCode() {
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
