package ru.shurupov.otus.architecture.eventloop.executor;

public class EventLoopActionSoftStop extends EventLoopAction {

  private final EventLoop eventLoop;

  public EventLoopActionSoftStop(EventLoop eventLoop) {
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
