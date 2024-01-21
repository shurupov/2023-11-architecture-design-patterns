package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Movable;

@RequiredArgsConstructor
public class MoveCommand implements Command {

  private final Movable movable;

  @Override
  public void execute() throws CommandException {
    movable.move(movable.getVelocity());
  }
}
