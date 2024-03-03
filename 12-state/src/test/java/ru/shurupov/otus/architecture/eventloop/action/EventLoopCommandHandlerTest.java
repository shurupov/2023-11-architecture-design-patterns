package ru.shurupov.otus.architecture.eventloop.action;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.eventloop.EventLoop;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.exception.HandlerSelector;

@ExtendWith(MockitoExtension.class)
class EventLoopCommandHandlerTest {

  @Mock
  private BlockingQueue<Command> commandQueue;
  @Mock
  private HandlerSelector handlerSelector;
  @Mock
  private EventLoop eventLoop;
  @Mock
  private Command command;
  @Mock
  private Command handler;

  private Command action;

  @BeforeEach
  public void init() {
    when(eventLoop.getQueue()).thenReturn(commandQueue);
    when(eventLoop.getHandlerSelector()).thenReturn(handlerSelector);

    action = new EventLoopCommandHandler(eventLoop);
  }

  @Test
  void givenCommandFromQueue_whenExecute_thenCommandExecuted() throws InterruptedException {
    when(commandQueue.take()).thenReturn(command);

    action.execute();

    verify(command, times(1)).execute();
  }

  @Test
  void givenCommandFromQueue_whenExecuteWithException_thenExceptionHandled() throws InterruptedException {
    CommandException e = new CommandException();

    when(commandQueue.take()).thenReturn(command);
    doThrow(e).when(command).execute();
    when(handlerSelector.getHandler(any(), any())).thenReturn(handler);

    action.execute();

    verify(command, times(1)).execute();
    verify(handlerSelector, times(1)).getHandler(eq(e), eq(command));
    verify(handler, times(1)).execute();
  }
}