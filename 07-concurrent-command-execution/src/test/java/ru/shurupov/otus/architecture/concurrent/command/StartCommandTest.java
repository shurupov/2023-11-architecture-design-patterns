package ru.shurupov.otus.architecture.concurrent.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoop;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopStarter;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

  @Mock
  private Command command1;
  @Mock
  private Command command2;
  @Mock
  private Command command3;

  private StartCommand startCommand;

  private BlockingQueue<Command> queue;
  private EventLoopStarter eventLoopStarter;

  @BeforeEach
  public void init() throws InterruptedException {

    queue = new LinkedBlockingQueue<>();
    queue.put(command1);
    queue.put(command2);
    queue.put(command3);

    eventLoopStarter = new EventLoopStarter(queue, new HandlerSelector());
    startCommand = new StartCommand(eventLoopStarter);
  }

  @Test
  void givenEventLoopStarter_whenExecute_thenCommandsFromQueueExecutedAndQueueIsEmpty()
      throws IllegalAccessException {
    startCommand.execute();

    await()
        .until(() -> queue.isEmpty());

    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
    verify(command3, times(1)).execute();

    assertThat(queue).isEmpty();

    Field eventLoopField = FieldUtils.getField(EventLoopStarter.class, "eventLoop", true);
    EventLoop eventLoop = (EventLoop) eventLoopField.get(eventLoopStarter);
    Field stopField = FieldUtils.getField(EventLoop.class, "stop", true);
    boolean stop = stopField.getBoolean(eventLoop);

    assertThat(stop).isFalse();
  }
}