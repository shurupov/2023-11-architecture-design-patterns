package ru.shurupov.otus.architecture.spacebattle.server.activity;

import ru.shurupov.otus.architecture.spacebattle.server.entity.Position;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Velocity;

public interface Movable {
  Position getPosition();
  Velocity getVelocity();
  void move(Velocity velocity);
}
