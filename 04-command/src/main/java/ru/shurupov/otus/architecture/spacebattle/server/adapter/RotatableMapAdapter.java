package ru.shurupov.otus.architecture.spacebattle.server.adapter;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Rotatable;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Angle;
import ru.shurupov.otus.architecture.spacebattle.server.entity.AngularVelocity;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToGetAngularVelocityException;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToGetDirectionException;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToRotateException;

@RequiredArgsConstructor
public class RotatableMapAdapter implements Rotatable {

  public static final String DIRECTION_ANGLE_PROPERTY_NAME = "directionAngle";
  public static final String DELTA_ANGLE_PROPERTY_NAME = "directionDeltaAngle";

  private final Map<String, Object> rotatableObject;

  @Override
  public Angle getDirection() {
    /* Checking whether object and property direction */
    if (rotatableObject != null && rotatableObject.containsKey(DIRECTION_ANGLE_PROPERTY_NAME)) {
      return () -> (Double) rotatableObject.get(DIRECTION_ANGLE_PROPERTY_NAME);
    } else {
      throw new UnableToGetDirectionException();
    }
  }

  @Override
  public AngularVelocity getDirectionAngularVelocity() throws UnableToGetAngularVelocityException {
    /* Checking whether object and property angular velocity */
    if (rotatableObject != null && rotatableObject.containsKey(DELTA_ANGLE_PROPERTY_NAME)) {
      return () -> (Double) rotatableObject.get(DELTA_ANGLE_PROPERTY_NAME);
    } else {
      throw new UnableToGetAngularVelocityException();
    }
  }

  @Override
  public void rotate(AngularVelocity deltaAngle) {
    try {
      rotatableObject.put(
          DIRECTION_ANGLE_PROPERTY_NAME,
          this.getDirection().getRad() + deltaAngle.getDeltaAngleRad()
      );
    } catch (Throwable e) {
      throw new UnableToRotateException(e);
    }
  }
}
