package ru.shurupov.otus.architecture.concurrent.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoop;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopStarter;

@ExtendWith(MockitoExtension.class)
class HardStopCommandTest {

  @Mock
  private Command command1;
  @Mock
  private Command command2;
  @Mock
  private Command command3;

  private StartCommand startCommand;

  private EventLoopStarter eventLoopStarter;
  private BlockingQueue<Command> queue;

  @BeforeEach
  public void init() throws InterruptedException {

    queue = new LinkedBlockingQueue<>();

    eventLoopStarter = new EventLoopStarter(queue, new HandlerSelector());
    startCommand = new StartCommand(eventLoopStarter);
    HardStopCommand hardStopCommand = new HardStopCommand(eventLoopStarter);

    queue.put(command1);
    queue.put(command2);
    queue.put(hardStopCommand);
    queue.put(command3);
  }

  @Test
  void givenEventLoopStarterWithHardStopCommandInQueue_whenHardStopExecuted_thenLoopStoppedAndOneCommandIsStillInQueue()
      throws IllegalAccessException {
    startCommand.execute();

    await()
        .until(() -> queue.size() == 1);

    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
    verify(command3, times(0)).execute();

    await()
        .atLeast(100, TimeUnit.MILLISECONDS)
        .atMost(1, TimeUnit.SECONDS);

    assertThat(queue).hasSize(1);

    Field eventLoopField = FieldUtils.getField(EventLoopStarter.class, "eventLoop", true);
    EventLoop eventLoop = (EventLoop) eventLoopField.get(eventLoopStarter);
    Field stopField = FieldUtils.getField(EventLoop.class, "stop", true);
    boolean stop = stopField.getBoolean(eventLoop);

    assertThat(stop).isTrue();
  }
}