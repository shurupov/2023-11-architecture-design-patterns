package ru.shurupov.otus.architecture.interpreter.impl;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.interpreter.abstraction.Acceleratable;

@RequiredArgsConstructor
public class ShipAcceleratableAdapter implements Acceleratable {

  private final Map<String, Object> ship;
  @Override
  public void accelerate(double acceleration) {
    double currentVelocity = (Double) ship.get("velocity");
    ship.put("velocity", currentVelocity + acceleration);
  }
}
