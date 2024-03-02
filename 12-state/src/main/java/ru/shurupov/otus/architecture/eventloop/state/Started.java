package ru.shurupov.otus.architecture.eventloop.state;

import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.EventLoopActionSoftStopAction;
import ru.shurupov.otus.architecture.eventloop.action.MoveToStopEventLoopAction;

@RequiredArgsConstructor
public class Started implements EventLoopState {

  private final EventLoop eventLoop;

  @Override
  public void start() {
  }

  @Override
  public void prepareToStop() {
    Queue<Command> tempQueue = new LinkedList<>();
    Command action = new MoveToStopEventLoopAction(eventLoop, tempQueue);
    eventLoop.setAction(action);
    eventLoop.setState(new PreparedToStop(eventLoop, tempQueue));
  }

  @Override
  public void softStop() {
    Command action = new EventLoopActionSoftStopAction(eventLoop);
    eventLoop.setAction(action);
    eventLoop.setState(new SoftStopped(eventLoop));
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
