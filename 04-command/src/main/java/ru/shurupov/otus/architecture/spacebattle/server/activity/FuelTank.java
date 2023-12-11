package ru.shurupov.otus.architecture.spacebattle.server.activity;

public interface FuelTank {

  int getFuelAmount();
  int getFuelPortion();
  void burnFuel(int portion);

}
