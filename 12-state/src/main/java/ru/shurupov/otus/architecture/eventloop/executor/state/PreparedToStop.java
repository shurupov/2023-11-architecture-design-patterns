package ru.shurupov.otus.architecture.eventloop.executor.state;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoop;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoopAction;

@RequiredArgsConstructor
public class PreparedToStop implements EventLoopState {

  private final EventLoop eventLoop;
  private final Queue<Command> tempQueue;

  @Override
  public void start() {
    Command action = new EventLoopAction(eventLoop);
    eventLoop.setAction(action);
    eventLoop.getQueue().addAll(tempQueue);
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
