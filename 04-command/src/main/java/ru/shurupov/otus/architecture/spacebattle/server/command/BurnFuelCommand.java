package ru.shurupov.otus.architecture.spacebattle.server.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.activity.FuelTank;

@RequiredArgsConstructor
public class BurnFuelCommand implements Command {

  private final FuelTank fuelTank;

  @Override
  public void execute() {
    fuelTank.burnFuel(fuelTank.getFuelPortion());
  }
}
