package ru.shurupov.otus.architecture.concurrent.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopThread;

@RequiredArgsConstructor
public class HardStopCommand implements Command {

  private final EventLoopThread eventLoopThread;

  @Override
  public void execute() {
    eventLoopThread.stop();
  }
}
