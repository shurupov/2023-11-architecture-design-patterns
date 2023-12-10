package ru.shurupov.otus.architecture.spacebattle.server.adapter;

public interface FuelTankAdapter {

  int getFuelAmount();
  int getFuelPortion();
  void burnFuel(int portion);

}
