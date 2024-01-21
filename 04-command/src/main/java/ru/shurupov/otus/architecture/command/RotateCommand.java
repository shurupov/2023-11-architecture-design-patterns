package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.abstraction.activity.Rotatable;

@RequiredArgsConstructor
public class RotateCommand implements Command {

  private final Rotatable rotatable;

  @Override
  public void execute() throws CommandException {
    rotatable.rotate(rotatable.getVelocity());
  }
}
