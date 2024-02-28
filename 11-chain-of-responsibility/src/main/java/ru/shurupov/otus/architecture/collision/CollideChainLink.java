package ru.shurupov.otus.architecture.collision;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CollideChainLink {

  private final CollideService collideService;
  @Getter
  private final Collidable collidable;
  @Getter
  private final CollideChainLink next;

  public Collidable collide(Collidable collidable) {
    boolean collided = collideService.canCollide(this.collidable, collidable);
    if (collided) {
      return this.collidable;
    }
    if (next == null) {
      return null;
    }

    return next.collide(collidable);
  }
}
