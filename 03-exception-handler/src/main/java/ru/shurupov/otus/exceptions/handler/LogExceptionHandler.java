package ru.shurupov.otus.exceptions.handler;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.exceptions.command.Command;
import ru.shurupov.otus.exceptions.command.ExceptionLogCommand;
import ru.shurupov.otus.exceptions.exception.BaseException;

@Slf4j
@RequiredArgsConstructor
public class LogExceptionHandler implements CommandExceptionHandler {

  private final Queue<Command> commandQueue;
  private final BaseException exception;

  @Override
  public void execute() throws BaseException {
    commandQueue.add(new ExceptionLogCommand(exception));
  }
}
