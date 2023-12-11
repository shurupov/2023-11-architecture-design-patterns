package ru.shurupov.otus.architecture.spacebattle.server.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Rotatable;
import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

@RequiredArgsConstructor
public class RotateCommand implements Command {

  private final Rotatable rotatable;

  @Override
  public void execute() throws CommandException {
    rotatable.rotate(rotatable.getDirectionAngularVelocity());
  }
}
