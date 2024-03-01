package ru.shurupov.otus.architecture.eventloop.action;

import ru.shurupov.otus.architecture.eventloop.EventLoop;

public class EventLoopActionSoftStopAction extends EventLoopAction {

  private final EventLoop eventLoop;

  public EventLoopActionSoftStopAction(EventLoop eventLoop) {
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
