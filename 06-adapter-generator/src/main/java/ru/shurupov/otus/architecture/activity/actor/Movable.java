package ru.shurupov.otus.architecture.activity.actor;

import ru.shurupov.otus.architecture.activity.entity.Position;
import ru.shurupov.otus.architecture.activity.entity.Velocity;

public interface Movable {
  Position getPosition();
  Velocity getVelocity();
  void move(Velocity velocity);
}
