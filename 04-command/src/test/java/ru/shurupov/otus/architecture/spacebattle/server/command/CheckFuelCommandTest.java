package ru.shurupov.otus.architecture.spacebattle.server.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.spacebattle.server.exception.NotEnoughFuelException;
import ru.shurupov.otus.architecture.spacebattle.server.adapter.FuelTankAdapter;
import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

@ExtendWith(MockitoExtension.class)
class CheckFuelCommandTest {

  private CheckFuelCommand checkFuelCommand;

  @Mock
  private FuelTankAdapter fuelTankAdapter;

  @BeforeEach
  public void init() {
    checkFuelCommand = new CheckFuelCommand(fuelTankAdapter);
  }

  @Test
  void givenLittleFuelTank_whenExecute_thenThrowException() {
    when(fuelTankAdapter.getFuelAmount()).thenReturn(5);
    when(fuelTankAdapter.getFuelPortion()).thenReturn(6);
    assertThatThrownBy(() -> checkFuelCommand.execute()).isInstanceOf(NotEnoughFuelException.class);
    verify(fuelTankAdapter, times(1)).getFuelAmount();
    verify(fuelTankAdapter, times(1)).getFuelPortion();
  }

  @Test
  void givenEnoughFuelTank_whenExecute_thenNothingHappened() throws CommandException {
    when(fuelTankAdapter.getFuelAmount()).thenReturn(10);
    when(fuelTankAdapter.getFuelPortion()).thenReturn(6);
    checkFuelCommand.execute();
    verify(fuelTankAdapter, times(1)).getFuelAmount();
    verify(fuelTankAdapter, times(1)).getFuelPortion();
  }
}