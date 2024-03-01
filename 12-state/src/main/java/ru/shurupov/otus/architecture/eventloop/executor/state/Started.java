package ru.shurupov.otus.architecture.eventloop.executor.state;

import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoop;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoopActionSoftStop;
import ru.shurupov.otus.architecture.eventloop.executor.MoveToStopEventLoopAction;

@RequiredArgsConstructor
public class Started implements EventLoopState {

  private final EventLoop eventLoop;

  @Override
  public void start() {
  }

  @Override
  public void prepareToStop() {
    Queue<Command> tempQueue = new LinkedList<>();
    Command action = new MoveToStopEventLoopAction(eventLoop.getQueue(), tempQueue);
    eventLoop.setAction(action);
    eventLoop.setState(new PreparedToStop(eventLoop, tempQueue));
  }

  @Override
  public void softStop() {
    Command action = new EventLoopActionSoftStop(eventLoop);
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
