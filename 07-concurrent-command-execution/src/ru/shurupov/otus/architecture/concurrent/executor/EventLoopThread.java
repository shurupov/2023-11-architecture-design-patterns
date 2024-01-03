package ru.shurupov.otus.architecture.concurrent.executor;

import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.command.Command;

@RequiredArgsConstructor
public class EventLoopThread {
  private final Thread thread;
  private final EventLoop eventLoop;
  private final Command action;

  public EventLoopThread(BlockingQueue<Command> queue, HandlerSelector handlerSelector) {
    this.action = new EventLoopAction(queue, handlerSelector);
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
