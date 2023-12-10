package ru.shurupov.otus.command.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.command.adapter.FuelTankAdapter;

@RequiredArgsConstructor
public class BurnFuelCommand implements Command {

  private final FuelTankAdapter fuelTank;

  @Override
  public void execute() {
    fuelTank.burnFuel(fuelTank.getFuelPortion());
  }
}
