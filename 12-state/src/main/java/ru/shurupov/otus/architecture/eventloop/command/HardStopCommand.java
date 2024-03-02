package ru.shurupov.otus.architecture.eventloop.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.eventloop.EventLoop;

@RequiredArgsConstructor
public class HardStopCommand implements ChangeStateCommand {

  private final EventLoop eventLoop;

  @Override
  public void execute() {
    eventLoop.stop();
  }
}
