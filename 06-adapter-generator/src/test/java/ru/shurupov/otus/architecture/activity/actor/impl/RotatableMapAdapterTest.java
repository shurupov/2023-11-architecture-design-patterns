package ru.shurupov.otus.architecture.activity.actor.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.assertj.core.api.ComparatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.activity.actor.Rotatable;
import ru.shurupov.otus.architecture.activity.entity.Angle;
import ru.shurupov.otus.architecture.activity.entity.AngularVelocity;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetAngularVelocityException;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetDirectionException;
import ru.shurupov.otus.architecture.activity.exception.UnableToRotateException;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.strategy.IoCFactory;

class RotatableMapAdapterTest {

  public static final String DIRECTION_ANGLE_PROPERTY_NAME = "directionAngle";
  public static final String DELTA_ANGLE_PROPERTY_NAME = "directionDeltaAngle";

  private static final double PRECISION = 0.0000001;

  private static final Comparator<Double> DOUBLE_COMPARATOR =
      ComparatorFactory.INSTANCE.doubleComparatorWithPrecision(PRECISION);

  private IoC ioc;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    ioc.resolve("IoC.Register", "Rotatable.Direction.Get", (Function<Object[], Angle>) objects -> {
      Map<String, Object> rotatableObject = (Map<String, Object>) objects[0];
      /* Checking whether object and property direction */
      if (rotatableObject != null && rotatableObject.containsKey(DIRECTION_ANGLE_PROPERTY_NAME)) {
        return () -> (Double) rotatableObject.get(DIRECTION_ANGLE_PROPERTY_NAME);
      } else {
        throw new UnableToGetDirectionException();
      }
    });

    ioc.resolve("IoC.Register", "Rotatable.Velocity.Get", (Function<Object[], AngularVelocity>) objects -> {
      Map<String, Object> rotatableObject = (Map<String, Object>) objects[0];
      /* Checking whether object and property angular velocity */
      if (rotatableObject != null && rotatableObject.containsKey(DELTA_ANGLE_PROPERTY_NAME)) {
        return () -> (Double) rotatableObject.get(DELTA_ANGLE_PROPERTY_NAME);
      } else {
        throw new UnableToGetAngularVelocityException();
      }
    });

    ioc.resolve("IoC.Register", "Rotatable.Rotate", (Consumer<Object[]>) objects -> {
      Map<String, Object> rotatableObject = (Map<String, Object>) objects[0];
      AngularVelocity deltaAngle = (AngularVelocity) objects[1];
      try {
        rotatableObject.put(
            DIRECTION_ANGLE_PROPERTY_NAME,
            (Double) rotatableObject.get(DIRECTION_ANGLE_PROPERTY_NAME) + deltaAngle.getDeltaAngleRad()
        );
      } catch (Throwable e) {
        throw new UnableToRotateException(e);
      }
    });
  }

  @Test
  void givenRotatableMapAdapterWithSetDirection_whenInvokeGetDirection_thenCorrectDirectionReturned() {
    Rotatable rotatable = new RotatableMapAdapter(
        ioc,
        Map.of(
            DIRECTION_ANGLE_PROPERTY_NAME, 2 * Math.PI / 8 //45 degrees
        )
    );

    assertThat(rotatable.getDirection().getRad())
        .usingComparator(DOUBLE_COMPARATOR)
        .isEqualTo(2 * Math.PI / 8);
  }

  @Test
  void givenRotatableMapAdapterWithoutSetDirection_whenInvokeGetDirection_thenExceptionThrown() {
    Rotatable rotatable = new RotatableMapAdapter(ioc, Map.of());

    assertThatThrownBy(rotatable::getDirection)
        .isInstanceOf(UnableToGetDirectionException.class);
  }

  @Test
  void givenRotatableMapAdapterWithSetAngularVelocity_whenInvokeGetDirectionAngularVelocity_thenCorrectVelocityReturned() {
    Rotatable rotatable = new RotatableMapAdapter(
        ioc,
        Map.of(
            DELTA_ANGLE_PROPERTY_NAME, 2 * Math.PI / 32 //11.25 degrees
        )
    );

    assertThat(rotatable.getDirectionAngularVelocity().getDeltaAngleRad())
        .usingComparator(DOUBLE_COMPARATOR)
        .isEqualTo(2 * Math.PI / 32);
  }

  @Test
  void givenRotatableMapAdapterWithoutSetAngularVelocity_whenInvokeGetDirection_thenExceptionThrown() {
    Rotatable rotatable = new RotatableMapAdapter(ioc, Map.of());

    assertThatThrownBy(rotatable::getDirectionAngularVelocity)
        .isInstanceOf(UnableToGetAngularVelocityException.class);
  }

  @Test
  void givenRotatableMapAdapterWithSetDirection_whenInvokeRotate_thenDirectionChangedCorrectly() {
    Rotatable rotatable = new RotatableMapAdapter(
        ioc,
        new HashMap<>(
            Map.of(DIRECTION_ANGLE_PROPERTY_NAME, 2 * Math.PI / 8)//45 degrees
        )
    );

    rotatable.rotate(() -> 2 * Math.PI / 32);

    assertThat(rotatable.getDirection().getRad())
        .usingComparator(DOUBLE_COMPARATOR)
        .isEqualTo(2 * Math.PI * (5d/32d));
  }

  @Test
  void givenRotatableMapAdapterWithoutSetDirection_whenInvokeRotate_thenExceptionThrown() {
    Rotatable rotatable = new RotatableMapAdapter(ioc, Map.of());

    assertThatThrownBy(() -> rotatable.rotate(() -> 2 * Math.PI / 32))
        .isInstanceOf(UnableToRotateException.class);
  }
}