package ru.shurupov.otus.architecture.spacebattle.server.activity;

import ru.shurupov.otus.architecture.spacebattle.server.entity.Angle;
import ru.shurupov.otus.architecture.spacebattle.server.entity.AngularVelocity;
import ru.shurupov.otus.architecture.exception.UnableToGetAngularVelocityException;

public interface Rotatable {
  Angle getDirection();
  AngularVelocity getDirectionAngularVelocity() throws UnableToGetAngularVelocityException;
  void rotate(AngularVelocity deltaAngle);
}
