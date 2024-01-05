package ru.shurupov.otus.architecture.generator.exception;

public class UnableToGenerateInstanceException extends RuntimeException {
  public UnableToGenerateInstanceException() {}

  public UnableToGenerateInstanceException(Throwable e) {
    super(e);
  }
}
