package ru.shurupov.otus.architecture.exception.handler;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.command.ExceptionLogCommand;
import ru.shurupov.otus.architecture.exception.CommandException;

@Slf4j
@RequiredArgsConstructor
public class LogExceptionHandler implements CommandExceptionHandler {

  private final Queue<Command> commandQueue;
  private final CommandException exception;

  @Override
  public void execute() throws CommandException {
    commandQueue.add(new ExceptionLogCommand(exception));
  }
}
