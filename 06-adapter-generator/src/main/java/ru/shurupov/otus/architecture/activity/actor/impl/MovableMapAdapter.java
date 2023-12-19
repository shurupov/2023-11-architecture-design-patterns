package ru.shurupov.otus.architecture.activity.actor.impl;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.activity.actor.Movable;
import ru.shurupov.otus.architecture.activity.entity.Position;
import ru.shurupov.otus.architecture.activity.entity.Velocity;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetPositionException;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetVelocityException;
import ru.shurupov.otus.architecture.activity.exception.UnableToMoveException;
import ru.shurupov.otus.architecture.ioc.IoC;

@RequiredArgsConstructor
public class MovableMapAdapter implements Movable {

  private final IoC ioc;
  private final Map<String, Object> movableObject;

  @Override
  public Position getPosition() {
    return ioc.resolve("Movable.Position.Get", movableObject);
  }

  @Override
  public Velocity getVelocity() {
    return ioc.resolve("Movable.Velocity.Get", movableObject);
  }

  @Override
  public void move(Velocity velocity) {
    ioc.resolve("Movable.Move", movableObject, velocity);
  }
}
