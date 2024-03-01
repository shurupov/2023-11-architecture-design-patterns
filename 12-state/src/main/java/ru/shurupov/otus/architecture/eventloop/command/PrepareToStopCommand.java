package ru.shurupov.otus.architecture.eventloop.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoop;
import ru.shurupov.otus.architecture.exception.CommandException;

@RequiredArgsConstructor
public class PrepareToStopCommand implements Command {

  private final EventLoop eventLoop;

  @Override
  public void execute() throws CommandException {
    eventLoop.prepareToStop();
  }
}
