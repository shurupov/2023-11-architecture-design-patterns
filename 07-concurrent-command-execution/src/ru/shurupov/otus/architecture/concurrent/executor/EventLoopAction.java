package ru.shurupov.otus.architecture.concurrent.executor;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.command.Command;
import ru.shurupov.otus.architecture.concurrent.exception.CommandException;

@Slf4j
@RequiredArgsConstructor
public class EventLoopAction implements Command {

  protected final BlockingQueue<Command> commandQueue;
  private final HandlerSelector handlerSelector;

  @Override
  public void execute() {
    Command command = null;
    try {
      command = commandQueue.take();
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
    try {
      command.execute();
    } catch (CommandException e) {
      handlerSelector.getHandler(e, command).execute();
    }
  }
}
