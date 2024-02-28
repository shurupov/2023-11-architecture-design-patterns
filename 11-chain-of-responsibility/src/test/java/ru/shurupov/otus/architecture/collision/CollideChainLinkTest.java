package ru.shurupov.otus.architecture.collision;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CollideChainLinkTest {

  @Mock
  private CollideService collideService;

  @Test
  void givenChainWithNoNextElements_whenCollide_thenReturnedNull() {
    CollideChainLink chain = new CollideChainLink(collideService, mock(Collidable.class), null);

    Collidable result = chain.collide(mock(Collidable.class));
    assertThat(result).isNull();

    verify(collideService, times(1)).canCollide(any(), any());
  }

  @Test
  void givenChainWithNextElements_whenCollideAndNoCollision_thenReturnedNull() {
    CollideChainLink chain = new CollideChainLink(collideService, mock(Collidable.class), null);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);

    Collidable result = chain.collide(mock(Collidable.class));
    assertThat(result).isNull();

    verify(collideService, times(4)).canCollide(any(), any());
  }

  @Test
  void givenChainWithNextElements_whenCollideAndCollision_thenReturnedCollidedObject() {
    Collidable object1 = mock(Collidable.class);
    Collidable object2 = mock(Collidable.class);

    when(collideService.canCollide(any(), any())).thenReturn(false);
    when(collideService.canCollide(object1, object2)).thenReturn(true);

    CollideChainLink chain = new CollideChainLink(collideService, mock(Collidable.class), null);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, object1, chain);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);

    Collidable result = chain.collide(object2);
    assertThat(result).isEqualTo(object1);

    verify(collideService, times(2)).canCollide(any(), any());
  }
}