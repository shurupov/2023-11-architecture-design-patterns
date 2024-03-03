package ru.shurupov.otus.architecture.eventloop.command;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;

@ExtendWith(MockitoExtension.class)
class HardStopCommandTest {

  @Mock
  private EventLoop eventLoop;

  private Command hardStopCommand;

  @BeforeEach
  public void init() {
    hardStopCommand = new HardStopCommand(eventLoop);
  }

  @Test
  public void givenEventLoop_whenExecute_thenEventLoopStopped() {
    hardStopCommand.execute();

    verify(eventLoop, times(1)).stop();
  }
}