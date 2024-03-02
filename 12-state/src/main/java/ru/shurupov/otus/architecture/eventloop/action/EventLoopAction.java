package ru.shurupov.otus.architecture.eventloop.action;

import java.util.concurrent.BlockingQueue;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

@Slf4j
public class EventLoopAction implements Command {

  protected final BlockingQueue<Command> commandQueue;
  private final HandlerSelector handlerSelector;

  public EventLoopAction(EventLoop eventLoop) {
    commandQueue = eventLoop.getQueue();
    handlerSelector = eventLoop.getHandlerSelector();
  }

  @Override
  public void execute() {
    Command command;
    try {
      command = commandQueue.take();
      execute(command);
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }

  protected void execute(Command command) {
    try {
      command.execute();
    } catch (CommandException e) {
      handlerSelector.getHandler(e, command).execute();
    }
  }
}
