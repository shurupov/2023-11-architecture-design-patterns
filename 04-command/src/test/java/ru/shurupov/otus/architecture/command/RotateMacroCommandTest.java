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
class RotateMacroCommandTest {

  private RotateMacroCommand rotateMacroCommand;

  @Mock
  private ModifyVelocityByRotationCommand modifyVelocityCommand;

  @Mock
  private RotateCommand rotateCommand;

  @BeforeEach
  void setUp() {
    rotateMacroCommand = new RotateMacroCommand(modifyVelocityCommand, rotateCommand);
  }
  @Test
  public void givenGoodCommands_whenExecute_thenRotated() throws CommandException {
    rotateMacroCommand.execute();

    verify(modifyVelocityCommand, times(1)).execute();
    verify(rotateCommand, times(1)).execute();
  }

  @Test
  public void givenModifyingFails_whenExecute_thenNotRotatedAndExceptionThrown() throws CommandException {

    doThrow(new NotEnoughFuelException()).when(modifyVelocityCommand).execute();

    assertThatThrownBy(() -> rotateMacroCommand.execute());

    verify(modifyVelocityCommand, times(1)).execute();
    verify(rotateCommand, times(0)).execute();
  }

}