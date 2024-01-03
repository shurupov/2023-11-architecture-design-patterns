package ru.shurupov.otus.architecture.concurrent.executor;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.command.Command;
import ru.shurupov.otus.architecture.concurrent.exception.CommandException;

@RequiredArgsConstructor
public class EventLoop implements Runnable {

  private final Queue<Command> commandQueue;
  private final HandlerSelector handlerSelector;

  @Override
  public void run() {
    while (!commandQueue.isEmpty()) {
      Command command = commandQueue.poll();
      try {
        command.execute();
      } catch (CommandException e) {
        handlerSelector.getHandler(e, command).execute();
      }
    }
  }
}
