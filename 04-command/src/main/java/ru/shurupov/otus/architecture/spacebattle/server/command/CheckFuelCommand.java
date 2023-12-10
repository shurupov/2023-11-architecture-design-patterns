package ru.shurupov.otus.architecture.spacebattle.server.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.exception.NotEnoughFuelException;
import ru.shurupov.otus.architecture.spacebattle.server.adapter.FuelTankAdapter;
import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

@RequiredArgsConstructor
public class CheckFuelCommand implements Command {

  private final FuelTankAdapter fuelTank;

  @Override
  public void execute() throws CommandException {
    if (fuelTank.getFuelAmount() < fuelTank.getFuelPortion()) {
      throw new NotEnoughFuelException();
    }
  }
}
