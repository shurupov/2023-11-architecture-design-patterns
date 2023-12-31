package ru.shurupov.otus.architecture.spacebattle.server.activity;

import ru.shurupov.otus.architecture.spacebattle.server.entity.Angle;
import ru.shurupov.otus.architecture.spacebattle.server.entity.AngularVelocity;

public interface Rotatable {
  Angle getDirection();
  AngularVelocity getDirectionAngularVelocity();
  void rotate(AngularVelocity deltaAngle);
}
