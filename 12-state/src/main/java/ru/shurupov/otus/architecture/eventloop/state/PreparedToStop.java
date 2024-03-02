package ru.shurupov.otus.architecture.eventloop.state;

import java.util.Queue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.CommandHandler;
import ru.shurupov.otus.architecture.eventloop.action.EventLoopCommandHandler;

@RequiredArgsConstructor
public class PreparedToStop implements EventLoopState {

  private final EventLoop eventLoop;
  @Getter
  private final Queue<Command> tempQueue;

  @Override
  public void start() {
    CommandHandler action = new EventLoopCommandHandler(eventLoop);
    eventLoop.setHandler(action);
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
