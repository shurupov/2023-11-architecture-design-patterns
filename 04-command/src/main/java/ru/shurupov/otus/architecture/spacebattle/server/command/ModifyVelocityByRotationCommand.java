package ru.shurupov.otus.architecture.spacebattle.server.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Acceleratable;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Movable;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Rotatable;
import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

@RequiredArgsConstructor
public class ModifyVelocityByRotationCommand implements Command {

  private final Movable movable;
  private final Rotatable rotatable;
  private final Acceleratable acceleratable;

  @Override
  public void execute() throws CommandException {
    acceleratable.accelerate(
        acceleratable.getRotationAcceleration(
            movable.getVelocity(), rotatable.getDirectionAngularVelocity()
        )
    );
  }
}
