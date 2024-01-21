package ru.shurupov.otus.architecture.command;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.abstraction.activity.Movable;
import ru.shurupov.otus.architecture.abstraction.entity.Velocity;

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