package ru.shurupov.otus.command.adapter;

public interface FuelTankAdapter {

  int getFuelAmount();
  int getFuelPortion();
  void burnFuel(int portion);

}
