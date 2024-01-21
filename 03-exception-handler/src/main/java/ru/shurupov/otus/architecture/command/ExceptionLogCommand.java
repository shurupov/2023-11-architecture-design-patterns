package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.exception.CommandException;

@Slf4j
@RequiredArgsConstructor
public class ExceptionLogCommand implements Command {

  private final CommandException exception;

  @Override
  public void execute() throws CommandException {
    log.error("Exception handled", exception);
  }
}
