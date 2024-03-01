package ru.shurupov.otus.architecture.eventloop.executor.state;

public class Stopped implements EventLoopState {

  @Override
  public void start() {
  }

  @Override
  public void prepareToStop() {
  }

  @Override
  public void softStop() {
  }

  @Override
  public void stop() {
  }

  @Override
  public boolean isRun() {
    return false;
  }
}
