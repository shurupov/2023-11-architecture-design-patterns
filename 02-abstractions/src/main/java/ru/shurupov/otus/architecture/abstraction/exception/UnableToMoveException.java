package ru.shurupov.otus.architecture.abstraction.exception;

public class UnableToMoveException extends RuntimeException {

  public UnableToMoveException(Throwable e) {
    super(e);
  }
}
