package ru.shurupov.architecture;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.architecture.command.Command;
import ru.shurupov.architecture.exception.BaseException;
import ru.shurupov.architecture.exception.HandlerSelector;

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
