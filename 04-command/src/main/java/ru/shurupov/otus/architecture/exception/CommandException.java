package ru.shurupov.otus.architecture.exception;

public class CommandException extends RuntimeException {
  public CommandException() {
  }

  public CommandException(Throwable e) {
    super(e);
  }

}
