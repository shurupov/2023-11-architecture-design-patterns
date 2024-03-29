package ru.shurupov.otus.architecture.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.exception.NotEnoughFuelException;
import ru.shurupov.otus.architecture.abstraction.activity.FuelTank;

@RequiredArgsConstructor
public class CheckFuelCommand implements Command {

  private final FuelTank fuelTank;

  @Override
  public void execute() throws CommandException {
    if (fuelTank.getFuelAmount() < fuelTank.getFuelPortion()) {
      throw new NotEnoughFuelException();
    }
  }
}
