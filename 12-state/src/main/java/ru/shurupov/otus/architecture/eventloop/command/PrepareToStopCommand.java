package ru.shurupov.otus.architecture.eventloop.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.exception.CommandException;

@RequiredArgsConstructor
public class PrepareToStopCommand implements ChangeStateCommand {

  private final EventLoop eventLoop;

  @Override
  public void execute() throws CommandException {
    eventLoop.prepareToStop();
  }
}
