package ru.shurupov.architecture.exception.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.architecture.command.Command;
import ru.shurupov.architecture.command.ExceptionLogCommand;
import ru.shurupov.architecture.exception.CommandException;

@ExtendWith(MockitoExtension.class)
class LogExceptionHandlerTest {

  private LogExceptionHandler handler;

  @Mock
  private Queue<Command> commandQueue;

  @Mock
  private CommandException exception;

  @BeforeEach
  public void init() {
    handler = new LogExceptionHandler(commandQueue, exception);
  }

  @Test
  void givenQueue_whenExecute_thenLogCommandAddedToQueue() {
    handler.execute();

    verify(commandQueue, times(1)).add(any(ExceptionLogCommand.class));
  }
}