package ru.shurupov.otus.architecture.concurrent.command;

import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopActionSoftStop;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopStarter;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

@RequiredArgsConstructor
public class SoftStopCommand implements Command {

  protected final BlockingQueue<Command> commandQueue;
  private final HandlerSelector handlerSelector;
  private final EventLoopStarter eventLoopStarter;

  @Override
  public void execute() {
    eventLoopStarter.updateBehavior(new EventLoopActionSoftStop(commandQueue, handlerSelector,
        eventLoopStarter));
  }
}
