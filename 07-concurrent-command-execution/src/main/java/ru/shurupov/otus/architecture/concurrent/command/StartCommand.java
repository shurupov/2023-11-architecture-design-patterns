package ru.shurupov.otus.architecture.concurrent.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopStarter;

@RequiredArgsConstructor
public class StartCommand implements Command {
  private final EventLoopStarter eventLoopStarter;

  @Override
  public void execute() {
    eventLoopStarter.start();
  }
}
