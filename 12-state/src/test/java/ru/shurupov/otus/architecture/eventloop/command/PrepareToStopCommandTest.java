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
class PrepareToStopCommandTest {

  @Mock
  private EventLoop eventLoop;

  private Command prepareToStopCommand;

  @BeforeEach
  public void init() {
    prepareToStopCommand = new PrepareToStopCommand(eventLoop);
  }

  @Test
  public void givenEventLoop_whenExecute_thenEventLoopStopped() {
    prepareToStopCommand.execute();

    verify(eventLoop, times(1)).prepareToStop();
  }
}