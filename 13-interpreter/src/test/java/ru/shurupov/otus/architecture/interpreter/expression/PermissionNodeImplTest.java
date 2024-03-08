package ru.shurupov.otus.architecture.interpreter.expression;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shurupov.otus.architecture.interpreter.util.CommonContextPreparation.prepareCommon;
import static ru.shurupov.otus.architecture.interpreter.util.PermissionNodePreparation.preparePermissionNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class PermissionNodeImplTest {

  private IoC ioc;
  private PermissionNode permissionNode;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    prepareCommon(ioc);
    preparePermissionNode(ioc);
  }

  @Test
  void givenIocWithPermissionsForUser_whenPermissionNodeForAccelerateInterpret_thenTrue() {
    permissionNode = new PermissionNodeImpl("player", "player1", "ship1", "accelerate");
    assertThat(permissionNode.interpret(ioc)).isTrue();
  }

  @Test
  void givenIocWithPermissionsForUser2_whenPermissionNodeForAccelerateInterpret_thenTrue() {
    permissionNode = new PermissionNodeImpl("player", "player1", "ship3", "accelerate");
    assertThat(permissionNode.interpret(ioc)).isTrue();
  }

  @Test
  void givenIocWithPermissionsForUser_whenPermissionNodeForRotateInterpret_thenTrue() {
    permissionNode = new PermissionNodeImpl("player", "player1", "ship1", "rotate");
    assertThat(permissionNode.interpret(ioc)).isTrue();
  }

  @Test
  void givenIocWithoutPermissionsForUser_whenPermissionNodeForJumpInterpret_thenFalse() {
    permissionNode = new PermissionNodeImpl("player", "player1", "ship1", "jump");
    assertThat(permissionNode.interpret(ioc)).isFalse();
  }

  @Test
  void givenIocWithPermissionForAnotherObjectForUser_whenPermissionNodeInterpret_thenFalse() {
    permissionNode = new PermissionNodeImpl("player", "player1", "ship2", "accelerate");
    assertThat(permissionNode.interpret(ioc)).isFalse();
  }
}