package ru.shurupov.otus.architecture.eventloop.action;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;

@ExtendWith(MockitoExtension.class)
class MoveToStopEventLoopCommandHandlerTest {

  @Mock
  private Command command;
  @Mock
  private EventLoop eventLoop;
  @Mock
  private BlockingQueue<Command> queue;
  @Mock
  private Queue<Command> tempQueue;

  private Command action;

  @BeforeEach
  public void init() {
    when(eventLoop.getQueue()).thenReturn(queue);
    action = new MoveToStopEventLoopCommandHandler(eventLoop, tempQueue);
  }

  @Test
  void givenCommandFromQueue_whenExecute_thenCommandMovedToTempQueue() throws InterruptedException {
    when(queue.take()).thenReturn(command);

    action.execute();

    verify(queue, times(1)).take();
    verify(tempQueue, times(1)).add(eq(command));
  }
}