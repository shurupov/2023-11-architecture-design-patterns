package ru.shurupov.otus.architecture.abstraction.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.shurupov.otus.architecture.abstraction.adapter.RotatableMapAdapter.DELTA_ANGLE_PROPERTY_NAME;
import static ru.shurupov.otus.architecture.abstraction.adapter.RotatableMapAdapter.DIRECTION_ANGLE_PROPERTY_NAME;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.ComparatorFactory;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.abstraction.activity.Rotatable;
import ru.shurupov.otus.architecture.abstraction.exception.UnableToGetAngularVelocityException;
import ru.shurupov.otus.architecture.abstraction.exception.UnableToGetDirectionException;
import ru.shurupov.otus.architecture.abstraction.exception.UnableToRotateException;

class RotatableMapAdapterTest {

  private static final double PRECISION = 0.0000001;

  private static final Comparator<Double> DOUBLE_COMPARATOR =
      ComparatorFactory.INSTANCE.doubleComparatorWithPrecision(PRECISION);

  @Test
  void givenRotatableMapAdapterWithSetDirection_whenInvokeGetDirection_thenCorrectDirectionReturned() {
    Rotatable rotatable = new RotatableMapAdapter(
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
    Rotatable rotatable = new RotatableMapAdapter(Map.of());

    assertThatThrownBy(rotatable::getDirection)
        .isInstanceOf(UnableToGetDirectionException.class);
  }

  @Test
  void givenRotatableMapAdapterWithSetAngularVelocity_whenInvokeGetDirectionAngularVelocity_thenCorrectVelocityReturned() {
    Rotatable rotatable = new RotatableMapAdapter(
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
    Rotatable rotatable = new RotatableMapAdapter(Map.of());

    assertThatThrownBy(rotatable::getDirectionAngularVelocity)
        .isInstanceOf(UnableToGetAngularVelocityException.class);
  }

  @Test
  void givenRotatableMapAdapterWithSetDirection_whenInvokeRotate_thenDirectionChangedCorrectly() {
    Rotatable rotatable = new RotatableMapAdapter(
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
    Rotatable rotatable = new RotatableMapAdapter(Map.of());

    assertThatThrownBy(() -> rotatable.rotate(() -> 2 * Math.PI / 32))
        .isInstanceOf(UnableToRotateException.class);
  }
}