package ru.shurupov.otus.architecture.eventloop.state;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.EventLoopAction;

@RequiredArgsConstructor
public class Init implements EventLoopState {

  private final EventLoop eventLoop;

  @Override
  public void start() {
    Command action = new EventLoopAction(eventLoop);
    eventLoop.setAction(action);
    eventLoop.setState(new Started(eventLoop));
    eventLoop.getThread().start();
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
