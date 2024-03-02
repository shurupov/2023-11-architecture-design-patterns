package ru.shurupov.otus.architecture.eventloop.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.SoftStopEventLoopCommandHandler;
import ru.shurupov.otus.architecture.eventloop.action.MoveToStopEventLoopCommandHandler;
import ru.shurupov.otus.architecture.eventloop.state.utils.Assertions;

@ExtendWith(MockitoExtension.class)
class StartedTest {

  @Mock
  private EventLoop eventLoop;

  private EventLoopState started;

  @BeforeEach
  public void init() {
    started = new Started(eventLoop);
  }

  @Test
  void givenEventLoop_whenStart_thenNothingChanged() {
    started.start();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenPrepareToStop_thenStateChanged() {
    started.prepareToStop();

    verify(eventLoop, times(1)).getQueue();
    verify(eventLoop, times(1)).setHandler(any(MoveToStopEventLoopCommandHandler.class));
    verify(eventLoop, times(1)).setState(any(PreparedToStop.class));
    verify(eventLoop, times(1))
        .setState(Assertions.argHasField(PreparedToStop.class, "eventLoop", eventLoop));
  }

  @Test
  public void givenEventLoop_whenSoftStop_thenStateChanged() {
    started.softStop();

    verify(eventLoop, times(1)).setHandler(any(SoftStopEventLoopCommandHandler.class));
    verify(eventLoop, times(1)).setState(any(SoftStopped.class));
    verify(eventLoop, times(1))
        .setState(Assertions.argHasField(SoftStopped.class, "eventLoop", eventLoop));
  }

  @Test
  public void givenEventLoop_whenStop_thenStateChanged() {
    started.stop();

    verify(eventLoop, times(1)).setState(any(Stopped.class));
  }

  @Test
  public void givenEventLoop_whenIsRun_thenTrueReturned() {
    assertThat(started.isRun()).isTrue();
  }
}