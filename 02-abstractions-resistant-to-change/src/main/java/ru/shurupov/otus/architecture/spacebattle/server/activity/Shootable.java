package ru.shurupov.otus.architecture.spacebattle.server.activity;

import ru.shurupov.otus.architecture.spacebattle.server.entity.Angle;

public interface Shootable {
  Movable shoot(Angle direction);
}
