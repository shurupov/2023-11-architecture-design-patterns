package ru.shurupov.otus.architecture.abstraction.activity;

import ru.shurupov.otus.architecture.abstraction.entity.Position;
import ru.shurupov.otus.architecture.abstraction.entity.Velocity;

public interface Movable {
  Position getPosition();
  Velocity getVelocity();
  void move(Velocity velocity);
}
