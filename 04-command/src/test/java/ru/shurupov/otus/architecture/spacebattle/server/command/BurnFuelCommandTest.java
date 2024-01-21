package ru.shurupov.otus.architecture.spacebattle.server.command;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.command.BurnFuelCommand;
import ru.shurupov.otus.architecture.spacebattle.server.activity.FuelTank;

@ExtendWith(MockitoExtension.class)
class BurnFuelCommandTest {

  private BurnFuelCommand burnFuelCommand;

  @Mock
  private FuelTank fuelTank;

  @BeforeEach
  public void init() {
    burnFuelCommand = new BurnFuelCommand(fuelTank);
  }

  @Test
  public void givenFuelTank_whenExecute_thenFuelVolumeReduced() {
    when(fuelTank.getFuelPortion()).thenReturn(5);

    burnFuelCommand.execute();

    verify(fuelTank, times(1)).burnFuel(eq(5));
  }
}