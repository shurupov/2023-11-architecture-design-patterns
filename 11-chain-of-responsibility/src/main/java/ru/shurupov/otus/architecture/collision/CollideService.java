package ru.shurupov.otus.architecture.collision;

public interface CollideService {

  boolean canCollide(Collidable collidable1, Collidable collidable2);

  void collide(Collidable collidable1, Collidable collidable2);
}
