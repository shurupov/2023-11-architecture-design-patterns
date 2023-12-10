package ru.shurupov.otus.architecture.spacebattle.server.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Movable;
import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

@RequiredArgsConstructor
public class MoveCommand implements Command {

  private final Movable movable;

  @Override
  public void execute() throws CommandException {
    movable.move(movable.getVelocity());
  }
}
