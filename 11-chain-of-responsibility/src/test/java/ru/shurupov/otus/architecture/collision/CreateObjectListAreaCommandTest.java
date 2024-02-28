package ru.shurupov.otus.architecture.collision;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.abstraction.entity.Position;
import ru.shurupov.otus.architecture.command.Command;

@ExtendWith(MockitoExtension.class)
class CreateObjectListAreaCommandTest {

  @Mock
  private CollideService collideService;
  private Queue<Command> commandQueue;
  private GameAreaConfig config;

  private List<Collidable> objects;

  private CreateObjectListAreaCommand command;

  @BeforeEach
  public void init() {
    config = GameAreaConfig.builder()
        .areaWidth(100)
        .areaHeight(100)
        .gameAreaWidth(200)
        .gameAreaHeight(200)
        .build();
    objects = new ArrayList<>();
    commandQueue = new LinkedList<>();
  }

  @Test
  void givenEmptyObjectList_whenExecute_thenAddedCommandsWithNullChains() {
    command = new CreateObjectListAreaCommand(config, objects, collideService, commandQueue, 1, 50);

    command.execute();

    assertThat(commandQueue).hasSize(4);
    for (Command command : commandQueue) {
      assertThat(command).isInstanceOf(ProcessCollideCommand.class);
      assertThat(command).hasFieldOrPropertyWithValue("chain", null);
      assertThat(command).hasFieldOrPropertyWithValue("collideService", collideService);
    }
  }

  @Test
  void givenObjectList_whenExecute_thenAddedCommandsWithChains() {
    for (int xi = 0; xi < 2; xi++) {
      for (int yi = 0; yi < 2; yi++) {
        for (int i = 0; i < 5; i++) {
          objects.add(new CollidableImpl(new PositionImpl(
              new double[] {
                  xi * config.getAreaWidth() + i,
                  yi * config.getAreaHeight() + i
              }
          )));
        }
      }
    }


    command = new CreateObjectListAreaCommand(config, objects, collideService, commandQueue, 1, 50);

    command.execute();

    assertThat(commandQueue).hasSize(4);
    for (Command command : commandQueue) {
      assertThat(command).isInstanceOf(ProcessCollideCommand.class);
      assertThat(command).hasFieldOrPropertyWithValue("collideService", collideService);
      ProcessCollideCommand collideCommand = (ProcessCollideCommand) command;
      CollideChainLink chain = collideCommand.getChain();
      int amount = 0;
      while (chain != null) {
        amount++;
        chain = chain.getNext();
      }
      assertThat(amount).isEqualTo(5);
    }
  }

  @Test
  void givenObjectListWithMultipleOffsetSteps_whenExecute_thenAddedCommandsWithChains() {
    for (int xi = 0; xi < 2; xi++) {
      for (int yi = 0; yi < 2; yi++) {
        for (int i = 0; i < 5; i++) {
          objects.add(new CollidableImpl(new PositionImpl(
              new double[] {
                  xi * config.getAreaWidth() + i,
                  yi * config.getAreaHeight() + i
              }
          )));
        }
      }
    }


    command = new CreateObjectListAreaCommand(config, objects, collideService, commandQueue, 3, 50);

    command.execute();


    assertThat(commandQueue).hasSize(12);

    int countOfFives = 0;
    int countOfZeros = 0;
    for (Command command : commandQueue) {
      assertThat(command).isInstanceOf(ProcessCollideCommand.class);
      assertThat(command).hasFieldOrPropertyWithValue("collideService", collideService);
      ProcessCollideCommand collideCommand = (ProcessCollideCommand) command;
      CollideChainLink chain = collideCommand.getChain();
      int amount = 0;
      while (chain != null) {
        amount++;
        chain = chain.getNext();
      }
      if (amount == 5) {
        countOfFives++;
      }
      if (amount == 0) {
        countOfZeros++;
      }
    }
    assertThat(countOfFives).isEqualTo(6);
    assertThat(countOfZeros).isEqualTo(6);
  }

  @Value
  public static class CollidableImpl implements Collidable {
    Position position;
  }

  @Value
  public static class PositionImpl implements Position {
    double[] coords;
  }
}
