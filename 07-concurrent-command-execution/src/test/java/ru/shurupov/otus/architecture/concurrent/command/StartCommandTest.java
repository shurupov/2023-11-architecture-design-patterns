package ru.shurupov.otus.architecture.concurrent.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.concurrent.HandlerSelector;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopStarter;

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

  @BeforeEach
  public void init() throws InterruptedException {

    queue = new LinkedBlockingQueue<>();
    queue.put(command1);
    queue.put(command2);
    queue.put(command3);

    HandlerSelector handlerSelector = new HandlerSelector();

    EventLoopStarter eventLoopStarter = new EventLoopStarter(queue, handlerSelector);
    startCommand = new StartCommand(eventLoopStarter);
  }

  @Test
  void givenEventLoopStarter_whenExecute_thenCommandsFromQueueExecutedAndQueueIsEmpty() {
    startCommand.execute();

    await().until(() -> queue.isEmpty());

    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
    verify(command3, times(1)).execute();

    assertThat(queue).isEmpty();
  }
}