package ru.shurupov.otus.architecture.collision;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.exception.CommandException;

@RequiredArgsConstructor
@Getter
public class ProcessCollideCommand implements Command {

  private final CollideService collideService;
  private final CollideChainLink chain;

  @Override
  public void execute() throws CommandException {
    if (chain != null && chain.getNext() != null) {
      Collidable object1 = chain.getCollidable();
      Collidable object2 = chain.getNext().collide(object1);
      if (object2 != null) {
        collideService.collide(object1, object2);
      }
    }
  }
}
