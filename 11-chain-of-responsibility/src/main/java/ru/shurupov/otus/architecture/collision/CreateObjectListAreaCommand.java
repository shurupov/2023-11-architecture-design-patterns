package ru.shurupov.otus.architecture.collision;

import java.util.List;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.exception.CommandException;

@RequiredArgsConstructor
public class CreateObjectListAreaCommand implements Command {

  private final GameAreaConfig gameAreaConfig;
  private final List<Collidable> objects;
  private final CollideService collideService;
  private final Queue<Command> queue;
  private final int areaSteps;
  private final int stepOffset;

  @Override
  public void execute() throws CommandException {

    int areaCountX = (int) Math.ceil((double) gameAreaConfig.getGameAreaWidth() / gameAreaConfig.getAreaWidth());
    int areaCountY = (int) Math.ceil((double) gameAreaConfig.getGameAreaHeight() / gameAreaConfig.getAreaHeight());

    for (int x = 0; x < areaCountX; x++) {
      for (int y = 0; y < areaCountY; y++) {
        for (int offset = 0; offset < areaSteps * stepOffset; offset += stepOffset) {
          CollideChainLink chain = createAreaChain(objects, x, y, offset);
          queue.add(new ProcessCollideCommand(collideService, chain));
        }
      }
    }
  }

  private CollideChainLink createAreaChain(List<Collidable> collidables, int x, int y, int offset) {
    CollideChainLink chain = null;
    for (Collidable collidable : collidables) {
      if (inArea(collidable, x, y, offset)) {
        chain = new CollideChainLink(collideService, collidable, chain);
      }
    }
    return chain;
  }

  private boolean inArea(Collidable collidable, int x, int y, int offset) {
    return collidable.getPosition().getCoords()[0] >= x*gameAreaConfig.getAreaWidth() + offset
        && collidable.getPosition().getCoords()[0] < (x+1)*gameAreaConfig.getAreaWidth() + offset
        && collidable.getPosition().getCoords()[1] >= y*gameAreaConfig.getAreaHeight() + offset
        && collidable.getPosition().getCoords()[1] < (y+1)* gameAreaConfig.getAreaHeight() + offset;
  }
}
