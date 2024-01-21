package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.exception.CommandException;

@RequiredArgsConstructor
public class RetryCommand implements Command {

  private final Command command;

  @Override
  public void execute() throws CommandException {
    command.execute();
  }
}
