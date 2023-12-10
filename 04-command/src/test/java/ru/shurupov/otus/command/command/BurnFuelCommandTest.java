package ru.shurupov.otus.command.command;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.command.adapter.FuelTankAdapter;

@ExtendWith(MockitoExtension.class)
class BurnFuelCommandTest {

  private BurnFuelCommand burnFuelCommand;

  @Mock
  private FuelTankAdapter fuelTankAdapter;

  @BeforeEach
  public void init() {
    burnFuelCommand = new BurnFuelCommand(fuelTankAdapter);
  }

  @Test
  public void givenFuelTank_whenExecute_thenFuelVolumeReduced() {
    when(fuelTankAdapter.getFuelPortion()).thenReturn(5);

    burnFuelCommand.execute();

    verify(fuelTankAdapter, times(1)).burnFuel(eq(5));
  }
}