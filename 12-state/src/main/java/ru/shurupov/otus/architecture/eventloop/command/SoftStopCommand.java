package ru.shurupov.otus.architecture.eventloop.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoop;

@RequiredArgsConstructor
public class SoftStopCommand implements Command {
  private final EventLoop eventLoop;

  @Override
  public void execute() {
    eventLoop.softStop();
  }
}
