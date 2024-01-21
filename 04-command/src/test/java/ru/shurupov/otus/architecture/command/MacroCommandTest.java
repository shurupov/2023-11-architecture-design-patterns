package ru.shurupov.otus.architecture.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.architecture.exception.CommandException;

@ExtendWith(MockitoExtension.class)
class MacroCommandTest {

  private MacroCommand macroCommand;

  @Mock
  private Command command1;
  @Mock
  private Command command2;
  @Mock
  private Command command3;

  @BeforeEach
  public void init() {
    macroCommand = new MacroCommand(List.of(command1, command2, command3));
  }

  @Test
  public void givenCommandsDontThrowExceptions_whenExecute_thenCommandExecuted()
      throws CommandException {
    macroCommand.execute();

    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
    verify(command3, times(1)).execute();
  }

  @Test
  public void givenFirstCommandThrowsException_whenExecute_thenThrowsExceptionOnlyFirstCommandExecuted()
      throws CommandException {

    doThrow(CommandException.class).when(command1).execute();

    assertThatThrownBy(() -> macroCommand.execute()).isOfAnyClassIn(CommandException.class);

    verify(command1, times(1)).execute();
    verify(command2, times(0)).execute();
    verify(command3, times(0)).execute();
  }

  @Test
  public void givenMiddleCommandThrowsException_whenExecute_thenThrowsExceptionFirstAndSecondCommandsExecuted()
      throws CommandException {

    doThrow(CommandException.class).when(command2).execute();

    assertThatThrownBy(() -> macroCommand.execute()).isOfAnyClassIn(CommandException.class);

    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
    verify(command3, times(0)).execute();
  }

  @Test
  public void givenLastCommandThrowsException_whenExecute_thenThrowsExceptionAllCommandsExecuted()
      throws CommandException {

    doThrow(CommandException.class).when(command3).execute();

    assertThatThrownBy(() -> macroCommand.execute()).isOfAnyClassIn(CommandException.class);

    verify(command1, times(1)).execute();
    verify(command2, times(1)).execute();
    verify(command3, times(1)).execute();
  }

}