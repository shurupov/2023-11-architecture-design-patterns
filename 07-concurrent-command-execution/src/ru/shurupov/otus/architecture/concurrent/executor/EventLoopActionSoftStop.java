package ru.shurupov.otus.architecture.concurrent.executor;

import java.util.concurrent.BlockingQueue;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.command.Command;

public class EventLoopActionSoftStop extends EventLoopAction {

  private final EventLoopThread eventLoopThread;

  public EventLoopActionSoftStop(
      BlockingQueue<Command> commandQueue,
      HandlerSelector handlerSelector, EventLoopThread eventLoopThread) {
    super(commandQueue, handlerSelector);
    this.eventLoopThread = eventLoopThread;
  }

  @Override
  public void execute() {
    if (commandQueue.isEmpty()) {
      eventLoopThread.stop();
    } else {
      super.execute();
    }
  }
}
