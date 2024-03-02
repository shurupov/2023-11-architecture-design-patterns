package ru.shurupov.otus.architecture.eventloop.state;

import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.CommandHandler;
import ru.shurupov.otus.architecture.eventloop.action.SoftStopEventLoopCommandHandler;
import ru.shurupov.otus.architecture.eventloop.action.MoveToStopEventLoopCommandHandler;

@RequiredArgsConstructor
public class Started implements EventLoopState {

  private final EventLoop eventLoop;

  @Override
  public void start() {
  }

  @Override
  public void prepareToStop() {
    Queue<Command> tempQueue = new LinkedList<>();
    CommandHandler handler = new MoveToStopEventLoopCommandHandler(eventLoop, tempQueue);
    eventLoop.setHandler(handler);
    eventLoop.setState(new PreparedToStop(eventLoop, tempQueue));
  }

  @Override
  public void softStop() {
    CommandHandler handler = new SoftStopEventLoopCommandHandler(eventLoop);
    eventLoop.setHandler(handler);
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
