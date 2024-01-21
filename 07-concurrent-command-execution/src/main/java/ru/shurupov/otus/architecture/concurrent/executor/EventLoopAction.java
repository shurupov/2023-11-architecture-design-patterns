package ru.shurupov.otus.architecture.concurrent.executor;

import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

@Slf4j
@RequiredArgsConstructor
public class EventLoopAction implements Command {

  protected final BlockingQueue<Command> commandQueue;
  private final HandlerSelector handlerSelector;

  @Override
  public void execute() {
    Command command;
    try {
      command = commandQueue.take();
      try {
        command.execute();
      } catch (CommandException e) {
        handlerSelector.getHandler(e, command).execute();
      }
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }
}
