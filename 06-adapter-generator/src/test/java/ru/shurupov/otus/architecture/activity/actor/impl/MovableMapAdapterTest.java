package ru.shurupov.otus.architecture.activity.actor.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.activity.actor.Movable;
import ru.shurupov.otus.architecture.activity.entity.Position;
import ru.shurupov.otus.architecture.activity.entity.Velocity;
import ru.shurupov.otus.architecture.activity.entity.impl.MovablePositionImpl;
import ru.shurupov.otus.architecture.activity.entity.impl.MovableVelocityImpl;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetPositionException;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetVelocityException;
import ru.shurupov.otus.architecture.activity.exception.UnableToMoveException;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.strategy.IoCFactory;

class MovableMapAdapterTest {

  public static final String COORDINATE_X_PROPERTY_NAME = "coordX";
  public static final String COORDINATE_Y_PROPERTY_NAME = "coordY";
  public static final String DELTA_COORDINATE_X_PROPERTY_NAME = "deltaCoordX";
  public static final String DELTA_COORDINATE_Y_PROPERTY_NAME = "deltaCoordY";

  private static final double PRECISION = 0.0001;

  private IoC ioc;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    //Adding Position
    ioc.resolve("IoC.Register", "Movable.Position.Get", (Function<Object[], Position>) objects -> {
      try {
        Map<String, Object> movableObject = (Map<String, Object>) objects[0];
        return new MovablePositionImpl(
            (Double) movableObject.get("coordX"),
            (Double) movableObject.get("coordY")
        );
      } catch (Throwable e) {
        /* If we can't get access to position, we throw the exception */
        throw new UnableToGetPositionException(e);
      }
    });

    //Adding Velocity
    ioc.resolve("IoC.Register", "Movable.Velocity.Get", (Function<Object[], Velocity>) objects -> {
      try {
        Map<String, Object> movableObject = (Map<String, Object>) objects[0];
        return new MovableVelocityImpl(
            (Double) movableObject.get("deltaCoordX"),
            (Double) movableObject.get("deltaCoordY")
        );
      } catch (Throwable e) {
        /* If we can't get access to velocity, we throw the exception */
        throw new UnableToGetVelocityException(e);
      }
    });

    //Move
    ioc.resolve("IoC.Register", "Movable.Move", (Consumer<Object[]>) objects -> {
      try {
        Map<String, Object> movableObject = (Map<String, Object>) objects[0];
        Velocity velocity = (Velocity) objects[1];
        movableObject.put(
            "coordX",
            (Double) movableObject.get("coordX") + velocity.getPositionDelta()[0]
        );
        movableObject.put(
            "coordY",
            (Double) movableObject.get("coordY") + velocity.getPositionDelta()[1]
        );
      } catch (Throwable e) {
        /* If we can't get position data or can't change position we throw the exception */
        throw new UnableToMoveException(e);
      }
    });
  }

  @Test
  void givenMovableMapAdapterWithSetPosition_whenGetPositionInvoked_thenCorrectPositionReturned() {
    Movable movable = new MovableMapAdapter(
        ioc,
        Map.of(
            COORDINATE_X_PROPERTY_NAME, 5d,
            COORDINATE_Y_PROPERTY_NAME, 10d
        )
    );

    assertThat(movable.getPosition().getCoords())
        .usingComparatorWithPrecision(PRECISION)
        .containsExactly(5d, 10d);
  }

  @Test
  void givenMovableMapAdapterWithoutSetPosition_whenGetPositionInvoked_thenExceptionThrown() {
    Movable movable = new MovableMapAdapter(ioc, Map.of());

    assertThatThrownBy(movable::getPosition)
        .isInstanceOf(UnableToGetPositionException.class);
  }

  @Test
  void givenMovableMapAdapterWithSetVelocity_whenGetVelocityInvoked_thenCorrectVelocityReturned() {
    Movable movable = new MovableMapAdapter(
        ioc,
        Map.of(
            DELTA_COORDINATE_X_PROPERTY_NAME, 5d,
            DELTA_COORDINATE_Y_PROPERTY_NAME, 10d
        )
    );

    assertThat(movable.getVelocity().getPositionDelta())
        .usingComparatorWithPrecision(PRECISION)
        .containsExactly(5d, 10d);
  }

  @Test
  void givenMovableMapAdapterWithoutSetVelocity_whenGetVelocityInvoked_thenExceptionThrown() {
    Movable movable = new MovableMapAdapter(ioc,Map.of());

    assertThatThrownBy(movable::getVelocity)
        .isInstanceOf(UnableToGetVelocityException.class);
  }

  @Test
  void givenMovableMapAdapterWithSetPosition_whenMoveByVelocity_thenPositionChanged() {
    Movable movable = new MovableMapAdapter(
        ioc,
        new HashMap<>(
            Map.of(
                COORDINATE_X_PROPERTY_NAME, 12d,
                COORDINATE_Y_PROPERTY_NAME, 5d
            )
        )
    );

    movable.move(() -> new double[]{-7d, 3d});

    assertThat(movable.getPosition().getCoords())
        .usingComparatorWithPrecision(PRECISION)
        .containsExactly(5d, 8d);
  }

  @Test
  void givenUninitiatedMapAdapterWithSetPosition_whenMoveByVelocity_thenExceptionThrown() {
    Movable movable = new MovableMapAdapter(
        ioc,
        Map.of(
            "coordX", 12d,
            "coordY", 5d
        )
    );

    Velocity velocity = () -> new double[]{5d, 5d};

    assertThatThrownBy(() -> movable.move(velocity))
        .isInstanceOf(UnableToMoveException.class);
  }

  @Test
  void givenUnmovableMapAdapterWithSetPosition_whenMoveByVelocity_thenExceptionThrown() {
    Movable movable = new MovableMapAdapter(
        ioc,
        new HashMap<>()
    );

    Velocity velocity = () -> new double[]{5d, 5d};

    assertThatThrownBy(() -> movable.move(velocity))
        .isInstanceOf(UnableToMoveException.class);
  }
}