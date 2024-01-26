package ru.shurupov.otus.architecture.generator.exception;

import ru.shurupov.otus.architecture.exception.CommandException;

public class UnableToGenerateInstanceException extends CommandException {
  public UnableToGenerateInstanceException() {}

  public UnableToGenerateInstanceException(Throwable e) {
    super(e);
  }
}
