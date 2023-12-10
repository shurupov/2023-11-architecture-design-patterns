package ru.shurupov.otus.architecture.spacebattle.server.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Movable;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Velocity;
import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

@ExtendWith(MockitoExtension.class)
class MoveCommandTest {

  private MoveCommand moveCommand;

  @Mock
  private Movable movable;
  @Mock
  private Velocity velocity;

  @BeforeEach
  void setUp() {
    moveCommand = new MoveCommand(movable);
  }

  @Test
  public void givenMovable_whenExecute_thenMoved() throws CommandException {
    when(movable.getVelocity()).thenReturn(velocity);

    moveCommand.execute();

    verify(movable, times(1)).getVelocity();
    verify(movable, times(1)).move(eq(velocity));
  }
}