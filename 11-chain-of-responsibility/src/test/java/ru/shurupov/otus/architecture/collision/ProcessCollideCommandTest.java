package ru.shurupov.otus.architecture.collision;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.abstraction.activity.Collideable;
import ru.shurupov.otus.architecture.command.Command;

@ExtendWith(MockitoExtension.class)
class ProcessCollideCommandTest {

  @Mock
  private CollideService collideService;

  @Test
  void givenNullChain_whenExecute_thenNoCollideExecuted() {
    Command command = new ProcessCollideCommand(collideService, null);

    command.execute();

    verify(collideService, never()).canCollide(any(), any());
    verify(collideService, never()).collide(any(), any());
  }

  @Test
  void givenChain_whenExecute_thenNoCollideExecuted() {
    CollideChainLink chain = new CollideChainLink(collideService, mock(Collidable.class), null);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);

    Command command = new ProcessCollideCommand(collideService, chain);
    command.execute();

    verify(collideService, times(2)).canCollide(any(), any());
    verify(collideService, never()).collide(any(), any());
  }

  @Test
  void givenChainWithLastCanCollide_whenExecute_thenCollideExecuted() {
    Collidable object1 = mock(Collidable.class, "object1");
    Collidable object2 = mock(Collidable.class, "object2");

    when(collideService.canCollide(any(), any())).thenReturn(false);
    when(collideService.canCollide(eq(object2), eq(object1))).thenReturn(true);

    CollideChainLink chain = new CollideChainLink(collideService, object2, null);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, object1, chain);

    Command command = new ProcessCollideCommand(collideService, chain);
    command.execute();

    verify(collideService, times(4)).canCollide(any(), any());
    verify(collideService, times(1)).collide(any(), any());
    verify(collideService, times(1)).collide(eq(object1), eq(object2));
  }

  @Test
  void givenChainWithMiddleCanCollide_whenExecute_thenCollideExecuted() {
    Collidable object1 = mock(Collidable.class, "object1");
    Collidable object2 = mock(Collidable.class, "object2");

    when(collideService.canCollide(any(), any())).thenReturn(false);
    when(collideService.canCollide(eq(object2), eq(object1))).thenReturn(true);

    CollideChainLink chain = new CollideChainLink(collideService, mock(Collidable.class), null);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, object2, chain);
    chain = new CollideChainLink(collideService, mock(Collidable.class), chain);
    chain = new CollideChainLink(collideService, object1, chain);

    Command command = new ProcessCollideCommand(collideService, chain);
    command.execute();

    verify(collideService, times(2)).canCollide(any(), any());
    verify(collideService, times(1)).collide(any(), any());
    verify(collideService, times(1)).collide(eq(object1), eq(object2));
  }
}