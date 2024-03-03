package ru.shurupov.otus.architecture.eventloop.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.eventloop.action.EventLoopCommandHandler;
import ru.shurupov.otus.architecture.eventloop.state.utils.Assertions;

@ExtendWith(MockitoExtension.class)
class SoftStoppedTest {

  @Mock
  private EventLoop eventLoop;

  private EventLoopState softStopped;

  @BeforeEach
  public void init() {
    softStopped = new SoftStopped(eventLoop);
  }

  @Test
  void givenEventLoop_whenStart_thenStateChangedToStarted() {
    softStopped.start();

    verify(eventLoop, times(1)).setHandler(any(EventLoopCommandHandler.class));
    verify(eventLoop, atLeastOnce()).getQueue();
    verify(eventLoop, times(1)).setState(any(Started.class));
    verify(eventLoop, times(1))
        .setState(Assertions.argHasField(Started.class, "eventLoop", eventLoop));
  }

  @Test
  public void givenEventLoop_whenPrepareToStop_thenStateChanged() {
    softStopped.prepareToStop();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenSoftStop_thenStateChanged() {
    softStopped.softStop();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenStop_thenStateChanged() {
    softStopped.stop();

    verify(eventLoop, times(1)).setState(any(Stopped.class));
  }

  @Test
  public void givenEventLoop_whenIsRun_thenTrueReturned() {
    assertThat(softStopped.isRun()).isTrue();
  }
}