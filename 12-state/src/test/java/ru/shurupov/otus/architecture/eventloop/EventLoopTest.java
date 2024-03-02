package ru.shurupov.otus.architecture.eventloop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.command.HardStopCommand;
import ru.shurupov.otus.architecture.eventloop.command.PrepareToStopCommand;
import ru.shurupov.otus.architecture.eventloop.command.RunCommand;
import ru.shurupov.otus.architecture.eventloop.command.SoftStopCommand;
import ru.shurupov.otus.architecture.eventloop.state.EventLoopState;
import ru.shurupov.otus.architecture.eventloop.state.PreparedToStop;
import ru.shurupov.otus.architecture.eventloop.state.Started;
import ru.shurupov.otus.architecture.eventloop.state.Stopped;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

@ExtendWith(MockitoExtension.class)
class EventLoopTest {

  @Mock
  private Command command1;
  @Mock
  private Command command2;
  @Mock
  private Command command3;
  @Mock
  private HandlerSelector handlerSelector;

  private BlockingQueue<Command> queue;

  private EventLoop eventLoop;

  @BeforeEach
  public void init() {
    queue = new LinkedBlockingQueue<>();
    eventLoop = new EventLoop(queue, handlerSelector);
  }

  @Test
  public void givenEventLoop_whenCreated_thenStarted() throws IllegalAccessException {
    assertThat(getState()).isInstanceOf(Started.class);
  }

  @Test
  public void givenEventLoop_whenHardStop_thenStopped()
      throws IllegalAccessException, InterruptedException {
    queue.put(new HardStopCommand(eventLoop));
    queue.put(command1);
    queue.put(command2);

    await()
        .until(() -> Stopped.class.equals(getState().getClass()));

    assertThat(queue).hasSize(2);
    assertThat(getState()).isInstanceOf(Stopped.class);
    assertThat(eventLoop.getThread().isAlive()).isFalse();
    verify(command1, never()).execute();
    verify(command2, never()).execute();
  }

  @Test
  public void givenEventLoop_whenSoftStop_thenStoppedAfterExistingCommands()
      throws IllegalAccessException, InterruptedException {
    queue.put(new SoftStopCommand(eventLoop));
    queue.put(command1);
    queue.put(command2);

    await()
        .until(() -> Stopped.class.equals(getState().getClass()));

    assertThat(queue).isEmpty();
    assertThat(getState()).isInstanceOf(Stopped.class);
    assertThat(eventLoop.getThread().isAlive()).isFalse();
    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
  }

  @Test
  public void givenEventLoop_whenPrepareStop_thenCommandsMovedToTempQueue()
      throws IllegalAccessException, InterruptedException {
    queue.put(new PrepareToStopCommand(eventLoop));
    queue.put(command1);
    queue.put(command2);

    await()
        .until(() -> queue.isEmpty());

    PreparedToStop preparedToStop = (PreparedToStop) getState();

    assertThat(preparedToStop.getTempQueue()).hasSize(2);
    assertThat(preparedToStop.getTempQueue()).contains(command1, command2);

    assertThat(queue).isEmpty();
    assertThat(getState()).isInstanceOf(PreparedToStop.class);
    assertThat(eventLoop.getThread().isAlive()).isTrue();
    verify(command1, never()).execute();
    verify(command2, never()).execute();
  }

  @Test
  public void givenEventLoop_whenPrepareStopAndStop_thenCommandsMovedToTempQueue()
      throws IllegalAccessException, InterruptedException {
    queue.put(new PrepareToStopCommand(eventLoop));
    queue.put(command1);
    queue.put(command2);

    await()
        .until(() -> queue.isEmpty());

    PreparedToStop preparedToStop = (PreparedToStop) getState();

    queue.put(new HardStopCommand(eventLoop));
    queue.put(command3);

    await()
        .until(() -> Stopped.class.equals(getState().getClass()));

    assertThat(preparedToStop.getTempQueue()).hasSize(2);
    assertThat(preparedToStop.getTempQueue()).contains(command1, command2);

    assertThat(queue).hasSize(1);
    assertThat(queue).contains(command3);
    assertThat(getState()).isInstanceOf(Stopped.class);
    assertThat(eventLoop.getThread().isAlive()).isFalse();
    verify(command1, never()).execute();
    verify(command2, never()).execute();
    verify(command3, never()).execute();
  }

  @Test
  public void givenEventLoop_whenPrepareStopAndStart_thenCommandsMovedToTempQueue()
      throws IllegalAccessException, InterruptedException {
    queue.put(new PrepareToStopCommand(eventLoop));
    queue.put(command1);
    queue.put(command2);

    await()
        .until(() -> queue.isEmpty());

    PreparedToStop preparedToStop = (PreparedToStop) getState();

    assertThat(preparedToStop.getTempQueue()).hasSize(2);
    assertThat(preparedToStop.getTempQueue()).contains(command1, command2);

    queue.put(new RunCommand(eventLoop));
    queue.put(command3);

    await()
        .until(() -> queue.isEmpty());

    assertThat(queue).isEmpty();
    assertThat(getState()).isInstanceOf(Started.class);
    assertThat(eventLoop.getThread().isAlive()).isTrue();
    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
    verify(command3, times(1)).execute();
  }

  private EventLoopState getState() throws IllegalAccessException {
    return (EventLoopState) FieldUtils.getField(EventLoop.class, "state", true).get(eventLoop);
  }

}