package ru.shurupov.otus.architecture.spacebattle.server.command;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.spacebattle.server.exception.NotEnoughFuelException;
import ru.shurupov.otus.architecture.spacebattle.server.activity.FuelTank;
import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

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
