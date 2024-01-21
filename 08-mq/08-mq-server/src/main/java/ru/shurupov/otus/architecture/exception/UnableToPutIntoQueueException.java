package ru.shurupov.otus.architecture.exception;

public class UnableToPutIntoQueueException extends CommandException {

  public UnableToPutIntoQueueException(Throwable e) {
    super(e);
  }
}
