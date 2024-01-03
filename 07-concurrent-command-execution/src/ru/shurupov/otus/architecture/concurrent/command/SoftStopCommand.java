package ru.shurupov.otus.architecture.concurrent.command;

import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopActionSoftStop;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopThread;

@RequiredArgsConstructor
public class SoftStopCommand implements Command {

  protected final BlockingQueue<Command> commandQueue;
  private final HandlerSelector handlerSelector;
  private final EventLoopThread eventLoopThread;

  @Override
  public void execute() {
    eventLoopThread.updateBehavior(new EventLoopActionSoftStop(commandQueue, handlerSelector, eventLoopThread));
  }
}
