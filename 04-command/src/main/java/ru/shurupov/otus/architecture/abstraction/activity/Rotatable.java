package ru.shurupov.otus.architecture.abstraction.activity;

import ru.shurupov.otus.architecture.abstraction.entity.AngularVelocity;
import ru.shurupov.otus.architecture.exception.UnableToGetAngularVelocityException;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Angle;

public interface Rotatable {
  Angle getDirection();
  AngularVelocity getDirectionAngularVelocity() throws UnableToGetAngularVelocityException;
  void rotate(AngularVelocity deltaAngle);
}
