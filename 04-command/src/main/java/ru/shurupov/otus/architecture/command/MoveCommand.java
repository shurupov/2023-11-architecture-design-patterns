package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.abstraction.activity.Movable;
import ru.shurupov.otus.architecture.exception.MoveCommandException;

@RequiredArgsConstructor
public class MoveCommand implements Command {

  private final Movable movable;

  @Override
  public void execute() throws CommandException {
    try {
      movable.move(movable.getVelocity());
    } catch (Throwable e) {
      throw new MoveCommandException(e);
    }
  }
}
