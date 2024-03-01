package ru.shurupov.otus.architecture.eventloop.action;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.command.Command;

@Slf4j
public class MoveToStopEventLoopAction implements Command {

  private final BlockingQueue<Command> commandQueue;

  @Getter
  private final Queue<Command> tempQueue;

  public MoveToStopEventLoopAction(BlockingQueue<Command> commandQueue, Queue<Command> tempQueue) {
    this.commandQueue = commandQueue;
    this.tempQueue = tempQueue;
  }

  @Override
  public void execute() {
    Command command;
    try {
      command = commandQueue.take();
      tempQueue.add(command);
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }
}
