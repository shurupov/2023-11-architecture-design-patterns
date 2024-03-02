package ru.shurupov.otus.architecture.eventloop.action;

import java.util.Queue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.command.ChangeStateCommand;

@Slf4j
public class MoveToStopEventLoopAction extends EventLoopAction {

  @Getter
  private final Queue<Command> tempQueue;

  public MoveToStopEventLoopAction(EventLoop eventLoop, Queue<Command> tempQueue) {
    super(eventLoop);
    this.tempQueue = tempQueue;
  }

  @Override
  public void execute() {
    Command command;
    try {
      command = commandQueue.take();

      //Don't know yet, how to get rid of this
      //Maybe I don't have to
      if (command instanceof ChangeStateCommand) {
        execute(command);
      } else {
        tempQueue.add(command);
      }
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }
}
