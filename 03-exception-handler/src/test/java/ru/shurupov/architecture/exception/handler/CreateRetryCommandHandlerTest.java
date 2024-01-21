package ru.shurupov.architecture.exception.handler;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.architecture.command.Command;
import ru.shurupov.architecture.command.RetryCommand;
import ru.shurupov.architecture.exception.handler.CreateRetryCommandHandler;

@ExtendWith(MockitoExtension.class)
class CreateRetryCommandHandlerTest {

  private CreateRetryCommandHandler handler;

  @Mock
  private Queue<Command> commandQueue;

  @Mock
  private Command command;

  @BeforeEach
  public void init() {
    handler = new CreateRetryCommandHandler(commandQueue, command);
  }

  @Test
  public void givenCommand_whenApply_thenAddedToQueue() {
    handler.execute();

    verify(commandQueue, times(1)).add(argThat(c -> {
      if (!(c instanceof RetryCommand retryCommand)) return false;

      try {
        Field field = RetryCommand.class.getDeclaredField("command");
        field.setAccessible(true);
        Command command1 = (Command) field.get(retryCommand);
        if (command1 != command) return false;
      } catch (NoSuchFieldException | IllegalAccessException e) {
        return false;
      }
      return true;
    }));
  }

}