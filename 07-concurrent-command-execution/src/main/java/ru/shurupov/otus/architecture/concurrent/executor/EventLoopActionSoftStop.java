package ru.shurupov.otus.architecture.concurrent.executor;

import java.util.concurrent.BlockingQueue;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.command.Command;

public class EventLoopActionSoftStop extends EventLoopAction {

  private final EventLoopStarter eventLoopStarter;

  public EventLoopActionSoftStop(
      BlockingQueue<Command> commandQueue,
      HandlerSelector handlerSelector, EventLoopStarter eventLoopStarter) {
    super(commandQueue, handlerSelector);
    this.eventLoopStarter = eventLoopStarter;
  }

  @Override
  public void execute() {
    if (commandQueue.isEmpty()) {
      eventLoopStarter.stop();
    } else {
      super.execute();
    }
  }
}
