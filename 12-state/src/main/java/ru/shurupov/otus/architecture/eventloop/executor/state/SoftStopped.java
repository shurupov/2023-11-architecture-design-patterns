package ru.shurupov.otus.architecture.eventloop.executor.state;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoop;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoopAction;

@RequiredArgsConstructor
public class SoftStopped implements EventLoopState {

  private final EventLoop eventLoop;

  @Override
  public void start() {
    Command action = new EventLoopAction(eventLoop);
    eventLoop.setAction(action);
    eventLoop.setState(new Started(eventLoop));
  }

  @Override
  public void prepareToStop() {
  }

  @Override
  public void softStop() {
  }

  @Override
  public void stop() {
    eventLoop.setState(new Stopped());
  }

  @Override
  public boolean isRun() {
    return true;
  }
}
