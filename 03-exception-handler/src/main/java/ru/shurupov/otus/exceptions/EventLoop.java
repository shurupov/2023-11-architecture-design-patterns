package ru.shurupov.otus.exceptions;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.exceptions.command.Command;
import ru.shurupov.otus.exceptions.exception.BaseException;

@RequiredArgsConstructor
public class EventLoop {

  private final Queue<Command> commandQueue;
  private final HandlerSelector handlerSelector;

  void update() {
    while (!commandQueue.isEmpty()) {
      Command command = commandQueue.poll();
      try {
        command.execute();
      } catch (BaseException e) {
        handlerSelector.getHandler(e, command).execute();
      }
    }
  }
}
