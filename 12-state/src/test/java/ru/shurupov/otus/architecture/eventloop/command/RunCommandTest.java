package ru.shurupov.otus.architecture.eventloop.command;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoop;

@ExtendWith(MockitoExtension.class)
class RunCommandTest {

  @Mock
  private EventLoop eventLoop;

  private Command runCommand;

  @BeforeEach
  public void init() {
    runCommand = new RunCommand(eventLoop);
  }

  @Test
  public void givenEventLoop_whenExecute_thenEventLoopStopped() {
    runCommand.execute();

    verify(eventLoop, times(1)).start();
  }
}