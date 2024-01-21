package ru.shurupov.architecture.exception.handler;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.architecture.command.Command;
import ru.shurupov.architecture.command.ExceptionLogCommand;
import ru.shurupov.architecture.exception.BaseException;

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
