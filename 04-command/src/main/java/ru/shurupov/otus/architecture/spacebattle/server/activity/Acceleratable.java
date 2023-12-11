package ru.shurupov.otus.architecture.spacebattle.server.activity;

import ru.shurupov.otus.architecture.spacebattle.server.entity.Acceleration;
import ru.shurupov.otus.architecture.spacebattle.server.entity.AngularVelocity;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Velocity;

public interface Acceleratable {
  void accelerate(Acceleration acceleration);

  //We need it, but I couldn't understand where to put it.
  Acceleration getRotationAcceleration(Velocity velocity, AngularVelocity angularVelocity);
}
