package ru.shurupov.otus.architecture.abstraction.activity;

import ru.shurupov.otus.architecture.abstraction.entity.Angle;

public interface Shootable {
  Movable shoot(Angle direction);
}
