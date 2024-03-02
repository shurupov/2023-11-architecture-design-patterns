package ru.shurupov.otus.architecture.eventloop.state;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.CommandHandler;
import ru.shurupov.otus.architecture.eventloop.action.EventLoopCommandHandler;

@RequiredArgsConstructor
public class SoftStopped implements EventLoopState {

  private final EventLoop eventLoop;

  @Override
  public void start() {
    CommandHandler action = new EventLoopCommandHandler(eventLoop);
    eventLoop.setHandler(action);
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
