package ru.shurupov.otus.architecture.spacebattle.server.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.adapter.FuelTankAdapter;

@RequiredArgsConstructor
public class BurnFuelCommand implements Command {

  private final FuelTankAdapter fuelTank;

  @Override
  public void execute() {
    fuelTank.burnFuel(fuelTank.getFuelPortion());
  }
}
