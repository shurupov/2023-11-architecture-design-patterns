package ru.shurupov.otus.architecture.eventloop.state;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoppedTest {

  private EventLoopState stopped;

  @BeforeEach
  public void init() {
    stopped = new Stopped();
  }

  @Test
  void givenEventLoop_whenStart_thenStateNotChanged() {
    stopped.start();
  }

  @Test
  public void givenEventLoop_whenPrepareToStop_thenStateChanged() {
    stopped.prepareToStop();
  }

  @Test
  public void givenEventLoop_whenSoftStop_thenStateChanged() {
    stopped.softStop();
  }

  @Test
  public void givenEventLoop_whenStop_thenStateChanged() {
    stopped.stop();
  }

  @Test
  public void givenEventLoop_whenIsRun_thenFalseReturned() {
    assertThat(stopped.isRun()).isFalse();
  }
}