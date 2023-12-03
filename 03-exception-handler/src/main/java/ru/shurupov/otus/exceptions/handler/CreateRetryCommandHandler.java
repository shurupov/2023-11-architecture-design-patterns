package ru.shurupov.otus.exceptions.handler;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.exceptions.command.Command;
import ru.shurupov.otus.exceptions.command.RetryCommand;

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
