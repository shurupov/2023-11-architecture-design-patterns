package ru.shurupov.otus.architecture.eventloop.executor.state;

public interface EventLoopState {

  void start();
  void prepareToStop();
  void softStop();
  void stop();

  boolean isRun();
}
