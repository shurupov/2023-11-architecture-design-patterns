package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.abstraction.activity.Acceleratable;
import ru.shurupov.otus.architecture.abstraction.activity.Movable;
import ru.shurupov.otus.architecture.abstraction.activity.Rotatable;

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
