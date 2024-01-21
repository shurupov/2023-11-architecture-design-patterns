package ru.shurupov.otus.architecture.abstraction.activity;

import ru.shurupov.otus.architecture.abstraction.entity.Angle;
import ru.shurupov.otus.architecture.abstraction.entity.AngularVelocity;

public interface Rotatable {
  Angle getDirection();
  AngularVelocity getDirectionAngularVelocity();
  void rotate(AngularVelocity deltaAngle);
}
