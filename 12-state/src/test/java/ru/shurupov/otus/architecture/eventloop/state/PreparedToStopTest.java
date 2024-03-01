package ru.shurupov.otus.architecture.eventloop.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.shurupov.otus.architecture.eventloop.state.utils.Assertions.argHasField;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.EventLoopAction;

@ExtendWith(MockitoExtension.class)
class PreparedToStopTest {

  @Mock
  private EventLoop eventLoop;
  @Mock
  private Queue<Command> tempQueue;
  @Mock
  private BlockingQueue<Command> queue;

  private EventLoopState preparedToStop;

  @BeforeEach
  public void init() {
    preparedToStop = new PreparedToStop(eventLoop, tempQueue);
  }

  @Test
  void givenEventLoop_whenStart_thenStateChangedToStarted() {
    when(eventLoop.getQueue()).thenReturn(queue);

    preparedToStop.start();

    verify(eventLoop, times(1)).setAction(any(EventLoopAction.class));
    verify(eventLoop, atLeastOnce()).getQueue();
    verify(queue, times(1)).addAll(eq(tempQueue));
    verify(eventLoop, times(1)).setState(any(Started.class));
    verify(eventLoop, times(1))
        .setState(argHasField(Started.class, "eventLoop", eventLoop));
  }

  @Test
  public void givenEventLoop_whenPrepareToStop_thenStateChanged() {
    preparedToStop.prepareToStop();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenSoftStop_thenStateChanged() {
    preparedToStop.softStop();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenStop_thenStateChanged() {
    preparedToStop.stop();

    verify(eventLoop, times(1)).setState(any(Stopped.class));
  }

  @Test
  public void givenEventLoop_whenIsRun_thenTrueReturned() {
    assertThat(preparedToStop.isRun()).isTrue();
  }
}