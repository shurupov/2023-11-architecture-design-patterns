package ru.shurupov.otus.command.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.command.adapter.FuelTankAdapter;
import ru.shurupov.otus.command.exception.CommandException;
import ru.shurupov.otus.command.exception.NotEnoughFuelException;

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
