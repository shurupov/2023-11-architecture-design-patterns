package ru.shurupov.otus.architecture.eventloop.state;

public interface EventLoopState {

  void start();
  void prepareToStop();
  void softStop();
  void stop();

  boolean isRun();
}
