package ru.shurupov.otus.architecture.eventloop.action;

import ru.shurupov.otus.architecture.eventloop.EventLoop;

public class SoftStopEventLoopCommandHandler extends EventLoopCommandHandler {

  private final EventLoop eventLoop;

  public SoftStopEventLoopCommandHandler(EventLoop eventLoop) {
    super(eventLoop);
    this.eventLoop = eventLoop;
  }

  @Override
  public void execute() {
    if (commandQueue.isEmpty()) {
      eventLoop.stop();
    } else {
      super.execute();
    }
  }
}
