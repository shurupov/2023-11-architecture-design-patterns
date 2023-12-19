package ru.shurupov.otus.architecture.activity.actor.impl;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.activity.actor.Rotatable;
import ru.shurupov.otus.architecture.activity.entity.Angle;
import ru.shurupov.otus.architecture.activity.entity.AngularVelocity;
import ru.shurupov.otus.architecture.ioc.IoC;

@RequiredArgsConstructor
public class RotatableMapAdapter implements Rotatable {

  private final IoC ioc;
  private final Map<String, Object> rotatableObject;

  @Override
  public Angle getDirection() {
    return ioc.resolve("Rotatable.Direction.Get", rotatableObject);
  }

  @Override
  public AngularVelocity getDirectionAngularVelocity() {
    return ioc.resolve("Rotatable.Velocity.Get", rotatableObject);
  }

  @Override
  public void rotate(AngularVelocity deltaAngle) {
    ioc.resolve("Rotatable.Rotate", rotatableObject, deltaAngle);
  }
}
