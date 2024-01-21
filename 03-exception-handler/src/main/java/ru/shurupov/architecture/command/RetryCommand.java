package ru.shurupov.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.architecture.exception.BaseException;

@RequiredArgsConstructor
public class RetryCommand implements Command {

  private final Command command;

  @Override
  public void execute() throws BaseException {
    command.execute();
  }
}
