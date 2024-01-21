package ru.shurupov.otus.architecture;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

@RequiredArgsConstructor
public class EventLoop {

  private final Queue<Command> commandQueue;
  private final HandlerSelector handlerSelector;

  void update() {
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
