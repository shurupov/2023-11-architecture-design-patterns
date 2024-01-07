package ru.shurupov.otus.architecture.concurrent.executor;

import ru.shurupov.otus.architecture.concurrent.command.Command;

public class EventLoop implements Runnable {

  private Command action;

  public EventLoop(Command action) {
    this.action = action;
  }

  private boolean stop = false;

  @Override
  public void run() {
    while (!stop) {
      action.execute();
    }
  }

  public void start() {
    stop = false;
  }

  public void stop() {
    stop = true;
  }

  public void updateAction(Command action) {
    this.action = action;
  }
}
