package ru.shurupov.otus.architecture.exception.handler;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.command.RetryCommand;

@Slf4j
@RequiredArgsConstructor
public class CreateRetryCommandHandler implements CommandExceptionHandler {

  private final Queue<Command> commandQueue;
  private final Command command;

  @Override
  public void execute() {
    commandQueue.add(new RetryCommand(command));
  }
}
