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
import ru.shurupov.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.command.CheckFuelCommand;
import ru.shurupov.otus.architecture.exception.NotEnoughFuelException;
import ru.shurupov.otus.architecture.spacebattle.server.activity.FuelTank;

@ExtendWith(MockitoExtension.class)
class CheckFuelCommandTest {

  private CheckFuelCommand checkFuelCommand;

  @Mock
  private FuelTank fuelTank;

  @BeforeEach
  public void init() {
    checkFuelCommand = new CheckFuelCommand(fuelTank);
  }

  @Test
  void givenLittleFuelTank_whenExecute_thenThrowException() {
    when(fuelTank.getFuelAmount()).thenReturn(5);
    when(fuelTank.getFuelPortion()).thenReturn(6);
    assertThatThrownBy(() -> checkFuelCommand.execute()).isInstanceOf(NotEnoughFuelException.class);
    verify(fuelTank, times(1)).getFuelAmount();
    verify(fuelTank, times(1)).getFuelPortion();
  }

  @Test
  void givenEnoughFuelTank_whenExecute_thenNothingHappened() throws CommandException {
    when(fuelTank.getFuelAmount()).thenReturn(10);
    when(fuelTank.getFuelPortion()).thenReturn(6);
    checkFuelCommand.execute();
    verify(fuelTank, times(1)).getFuelAmount();
    verify(fuelTank, times(1)).getFuelPortion();
  }
}