package ru.shurupov.otus.architecture.activity.actor;

import ru.shurupov.otus.architecture.activity.entity.Angle;
import ru.shurupov.otus.architecture.activity.entity.AngularVelocity;

public interface Rotatable {
  Angle getDirection();
  AngularVelocity getVelocity();
  void rotate(AngularVelocity deltaAngle);
}
