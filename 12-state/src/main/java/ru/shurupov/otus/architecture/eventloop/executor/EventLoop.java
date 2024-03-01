package ru.shurupov.otus.architecture.eventloop.executor;

import java.util.concurrent.BlockingQueue;
import lombok.Getter;
import lombok.Setter;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.executor.state.EventLoopState;
import ru.shurupov.otus.architecture.eventloop.executor.state.Init;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

public class EventLoop implements Runnable {

  @Setter
  private Command action;
  @Getter
  private final Thread thread;
  @Getter
  private final BlockingQueue<Command> queue;
  @Getter
  private final HandlerSelector handlerSelector;
  @Setter
  private EventLoopState state;

  public EventLoop(BlockingQueue<Command> queue, HandlerSelector handlerSelector) {
    this.queue = queue;
    this.handlerSelector = handlerSelector;
    state = new Init(this);
    thread = new Thread(this);
    start();
  }

  public void start() {
    state.start();
  }

  public void prepareToStop() {
    state.prepareToStop();
  }

  public void stop() {
    state.stop();
  }

  public void softStop() {
    state.softStop();
  }

  @Override
  public void run() {
    while (!state.isRun()) {
      action.execute();
    }
  }
}
