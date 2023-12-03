package ru.shurupov.otus.exceptions.command;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RetryCommandTest {

  private RetryCommand retryCommand;

  @Mock
  private Command command;

  @BeforeEach
  public void init() {
    retryCommand = new RetryCommand(command);
  }

  @Test
  void givenCommand_whenExecute_thenCommandExecuted() {
    retryCommand.execute();

    verify(command, times(1)).execute();
  }
}