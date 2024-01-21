package ru.shurupov.otus.architecture.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.exception.NotEnoughFuelException;

@ExtendWith(MockitoExtension.class)
class MoveMacroCommandTest {

  private MoveMacroCommand moveMacroCommand;

  @Mock
  private CheckFuelCommand checkFuelCommand;
  @Mock
  private BurnFuelCommand burnFuelCommand;
  @Mock
  private MoveCommand moveCommand;

  @BeforeEach
  public void init() {
    moveMacroCommand = new MoveMacroCommand(checkFuelCommand, burnFuelCommand, moveCommand);
  }

  @Test
  public void givenGoodCommands_whenExecute_thenMoved() throws CommandException {
    moveMacroCommand.execute();

    verify(checkFuelCommand, times(1)).execute();
    verify(burnFuelCommand, times(1)).execute();
    verify(moveCommand, times(1)).execute();
  }

  @Test
  public void givenCheckFails_whenExecute_thenNotMovedAndExceptionThrown() throws CommandException {

    doThrow(new NotEnoughFuelException()).when(checkFuelCommand).execute();

    assertThatThrownBy(() -> moveMacroCommand.execute());

    verify(checkFuelCommand, times(1)).execute();
    verify(burnFuelCommand, times(0)).execute();
    verify(moveCommand, times(0)).execute();
  }

}