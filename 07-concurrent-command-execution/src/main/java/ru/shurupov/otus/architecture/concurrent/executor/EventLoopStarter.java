package ru.shurupov.otus.architecture.concurrent.executor;

import java.util.concurrent.BlockingQueue;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.command.Command;

public class EventLoopStarter {
  private final Thread thread;
  private final EventLoop eventLoop;

  public EventLoopStarter(BlockingQueue<Command> queue, HandlerSelector handlerSelector) {
    Command action = new EventLoopAction(queue, handlerSelector);
    this.eventLoop = new EventLoop(action);
    thread = new Thread(eventLoop);
  }

  public void start() {
    eventLoop.start();
    thread.start();
  }

  public void stop() {
    eventLoop.stop();
  }

  public void updateBehavior(Command action) {
    eventLoop.updateAction(action);
  }
}
