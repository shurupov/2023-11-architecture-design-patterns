package ru.shurupov.otus.architecture.activity.actor;

import ru.shurupov.otus.architecture.activity.entity.Angle;
import ru.shurupov.otus.architecture.activity.entity.AngularVelocity;

public interface Rotatable {
  Angle getDirection();
  AngularVelocity getDirectionAngularVelocity();
  void rotate(AngularVelocity deltaAngle);
}
