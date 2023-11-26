package ru.shurupov.otus.architecture.spacebattle.server.adapter;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToGetPositionException;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToGetVelocityException;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToMoveException;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Movable;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Position;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Velocity;

@RequiredArgsConstructor
public class MovableMapAdapter implements Movable {

  public static final String COORDINATE_X_PROPERTY_NAME = "coordX";
  public static final String COORDINATE_Y_PROPERTY_NAME = "coordY";
  public static final int DELTA_COORDINATE_X_ELEMENT_INDEX = 0;
  public static final int DELTA_COORDINATE_Y_ELEMENT_INDEX = 1;
  public static final String DELTA_COORDINATE_X_PROPERTY_NAME = "deltaCoordX";
  public static final String DELTA_COORDINATE_Y_PROPERTY_NAME = "deltaCoordY";

  private final Map<String, Object> movableObject;

  @Override
  public Position getPosition() {
    try {
      return new MovablePositionImpl(
          (Double) movableObject.get(COORDINATE_X_PROPERTY_NAME),
          (Double) movableObject.get(COORDINATE_Y_PROPERTY_NAME)
      );
    } catch (Throwable e) {
      /* If we can't get access to position, we throw the exception */
      throw new UnableToGetPositionException(e);
    }
  }

  @Override
  public Velocity getVelocity() {
    try {
      return new MovableVelocityImpl(
          (Double) movableObject.get(DELTA_COORDINATE_X_PROPERTY_NAME),
          (Double) movableObject.get(DELTA_COORDINATE_Y_PROPERTY_NAME)
      );
    } catch (Throwable e) {
      /* If we can't get access to velocity, we throw the exception */
      throw new UnableToGetVelocityException(e);
    }
  }

  @Override
  public void move(Velocity velocity) {
    try {
      movableObject.put(
          COORDINATE_X_PROPERTY_NAME,
          (Double) movableObject.get(COORDINATE_X_PROPERTY_NAME)
              + velocity.getPositionDelta()[DELTA_COORDINATE_X_ELEMENT_INDEX]
      );
      movableObject.put(
          COORDINATE_Y_PROPERTY_NAME,
          (Double) movableObject.get(COORDINATE_Y_PROPERTY_NAME)
              + velocity.getPositionDelta()[DELTA_COORDINATE_Y_ELEMENT_INDEX]
      );
    } catch (Throwable e) {
      /* If we can't get position data or can't change position we throw the exception */
      throw new UnableToMoveException(e);
    }
  }

  @RequiredArgsConstructor
  private static class MovablePositionImpl implements Position {

    private final double x;
    private final double y;

    @Override
    public double[] getCoords() {
      return new double[] {x, y};
    }
  }

  @RequiredArgsConstructor
  private static class MovableVelocityImpl implements Velocity {

    private final double deltaX;
    private final double deltaY;

    @Override
    public double[] getPositionDelta() {
      return new double[] {deltaX, deltaY};
    }
  }
}
