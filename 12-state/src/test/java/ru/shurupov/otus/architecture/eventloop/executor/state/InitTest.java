package ru.shurupov.otus.architecture.eventloop.executor.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoop;
import ru.shurupov.otus.architecture.eventloop.executor.EventLoopAction;
import ru.shurupov.otus.architecture.eventloop.executor.state.utils.Assertions;

@ExtendWith(MockitoExtension.class)
class InitTest {
  @Mock
  private Thread thread;
  @Mock
  private EventLoop eventLoop;

  private EventLoopState init;

  @BeforeEach
  public void init() {
    init = new Init(eventLoop);
  }

  @Test
  public void givenEventLoop_whenStart_thenStateChangedToStarted() {
    when(eventLoop.getThread()).thenReturn(thread);

    init.start();

    verify(eventLoop, times(1)).setAction(any(EventLoopAction.class));
    verify(thread, times(1)).start();
    verify(eventLoop, times(1)).setState(any(Started.class));
    verify(eventLoop, times(1))
        .setState(Assertions.argHasField(Started.class, "eventLoop", eventLoop));
  }

  @Test
  public void givenEventLoop_whenPrepareToStop_thenStateChanged() {
    init.prepareToStop();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenSoftStop_thenStateChanged() {
    init.softStop();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenStop_thenStateChanged() {
    init.stop();

    verify(eventLoop, never()).setState(any());
  }

  @Test
  public void givenEventLoop_whenIsRun_thenFalseReturned() {
    assertThat(init.isRun()).isFalse();
  }
}