package ru.shurupov.otus.exceptions.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.exceptions.exception.BaseException;

@RequiredArgsConstructor
public class RetryCommand implements Command {

  private final Command command;

  @Override
  public void execute() throws BaseException {
    command.execute();
  }
}
