package ru.shurupov.architecture.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.architecture.exception.BaseException;

@Slf4j
@RequiredArgsConstructor
public class ExceptionLogCommand implements Command {

  private final BaseException exception;

  @Override
  public void execute() throws BaseException {
    log.error("Exception handled", exception);
  }
}
