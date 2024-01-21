package ru.shurupov.otus.architecture.abstraction.activity;

public interface FuelTank {

  int getFuelAmount();
  int getFuelPortion();
  void burnFuel(int portion);

}
