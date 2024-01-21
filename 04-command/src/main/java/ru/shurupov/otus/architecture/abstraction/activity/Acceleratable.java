package ru.shurupov.otus.architecture.abstraction.activity;

import ru.shurupov.otus.architecture.abstraction.entity.Acceleration;
import ru.shurupov.otus.architecture.abstraction.entity.AngularVelocity;
import ru.shurupov.otus.architecture.abstraction.entity.Velocity;

public interface Acceleratable {
  void accelerate(Acceleration acceleration);

  //We need it, but I couldn't understand where to put it.
  Acceleration getRotationAcceleration(Velocity velocity, AngularVelocity angularVelocity);
}
