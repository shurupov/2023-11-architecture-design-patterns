package ru.shurupov.otus.architecture.spacebattle.server.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.shurupov.otus.architecture.spacebattle.server.adapter.MovableMapAdapter.COORDINATE_X_PROPERTY_NAME;
import static ru.shurupov.otus.architecture.spacebattle.server.adapter.MovableMapAdapter.COORDINATE_Y_PROPERTY_NAME;
import static ru.shurupov.otus.architecture.spacebattle.server.adapter.MovableMapAdapter.DELTA_COORDINATE_X_PROPERTY_NAME;
import static ru.shurupov.otus.architecture.spacebattle.server.adapter.MovableMapAdapter.DELTA_COORDINATE_Y_PROPERTY_NAME;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToGetPositionException;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToGetVelocityException;
import ru.shurupov.otus.architecture.spacebattle.server.exception.UnableToMoveException;
import ru.shurupov.otus.architecture.spacebattle.server.activity.Movable;
import ru.shurupov.otus.architecture.spacebattle.server.entity.Velocity;

class MovableMapAdapterTest {

  private static final double PRECISION = 0.0001;

  @Test
  void givenMovableMapAdapterWithSetPosition_whenGetPositionInvoked_thenCorrectPositionReturned() {
    Movable movable = new MovableMapAdapter(
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
    Movable movable = new MovableMapAdapter(Map.of());

    assertThatThrownBy(movable::getPosition)
        .isInstanceOf(UnableToGetPositionException.class);
  }

  @Test
  void givenMovableMapAdapterWithSetVelocity_whenGetVelocityInvoked_thenCorrectVelocityReturned() {
    Movable movable = new MovableMapAdapter(
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
    Movable movable = new MovableMapAdapter(Map.of());

    assertThatThrownBy(movable::getVelocity)
        .isInstanceOf(UnableToGetVelocityException.class);
  }

  @Test
  void givenMovableMapAdapterWithSetPosition_whenMoveByVelocity_thenPositionChanged() {
    Movable movable = new MovableMapAdapter(
        new HashMap<>(
          Map.of(
              COORDINATE_X_PROPERTY_NAME, 12d,
              COORDINATE_Y_PROPERTY_NAME, 5d
          )
        )
    );

    movable.move(() -> new double[] {-7d, 3d});

    assertThat(movable.getPosition().getCoords())
        .usingComparatorWithPrecision(PRECISION)
        .containsExactly(5d, 8d);
  }

  @Test
  void givenUninitiatedMapAdapterWithSetPosition_whenMoveByVelocity_thenExceptionThrown() {
    Movable movable = new MovableMapAdapter(
        Map.of(
            "coordX", 12d,
            "coordY", 5d
        )
    );

    Velocity velocity = () -> new double[] {5d, 5d};

    assertThatThrownBy(() -> movable.move(velocity))
        .isInstanceOf(UnableToMoveException.class);
  }

  @Test
  void givenUnmovableMapAdapterWithSetPosition_whenMoveByVelocity_thenExceptionThrown() {
    Movable movable = new MovableMapAdapter(
        new HashMap<>()
    );

    Velocity velocity = () -> new double[] {5d, 5d};

    assertThatThrownBy(() -> movable.move(velocity))
        .isInstanceOf(UnableToMoveException.class);
  }
}