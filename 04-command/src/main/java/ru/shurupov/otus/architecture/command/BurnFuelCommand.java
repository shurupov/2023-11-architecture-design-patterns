package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.abstraction.activity.FuelTank;

@RequiredArgsConstructor
public class BurnFuelCommand implements Command {

  private final FuelTank fuelTank;

  @Override
  public void execute() {
    fuelTank.burnFuel(fuelTank.getFuelPortion());
  }
}
