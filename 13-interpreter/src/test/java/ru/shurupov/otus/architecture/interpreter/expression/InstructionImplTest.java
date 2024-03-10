package ru.shurupov.otus.architecture.interpreter.expression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.shurupov.otus.architecture.interpreter.util.CheckObjectNodePreparation.prepareCheckObjectNode;
import static ru.shurupov.otus.architecture.interpreter.util.CommonContextPreparation.prepareCommon;
import static ru.shurupov.otus.architecture.interpreter.util.ControlActionNodePreparation.prepareControlActionNode;
import static ru.shurupov.otus.architecture.interpreter.util.PermissionNodePreparation.preparePermissionNode;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.interpreter.exception.ControlPermissionCommandException;
import ru.shurupov.otus.architecture.interpreter.exception.ObjectNotFoundCommandException;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class InstructionImplTest {

  private IoC ioc;

  private Instruction instruction;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    prepareCommon(ioc);
    preparePermissionNode(ioc);
    prepareCheckObjectNode(ioc);
    prepareControlActionNode(ioc);
  }

  @Test
  void givenPermissionAndObject_whenInterpret_thenVelocityUpdated() {
    instruction = new InstructionImpl(
        new PermissionNodeImpl("player", "player1", "ship1", "accelerate"),
        new CheckObjectNodeImpl("ship1", "ship"),
        new ActionNodeImpl("ship1", "accelerate", "1")
    );

    Command command = instruction.interpret(ioc);

    command.execute();

    Map<String, Object> ship = ioc.resolve("ship1");

    assertThat(ship.get("velocity")).isEqualTo(1d);
  }

  @Test
  void givenObjectWithoutPermission_whenInterpret_thenExceptionThrownUpdated() {
    instruction = new InstructionImpl(
        new PermissionNodeImpl("player", "player1", "ship1", "jump"),
        new CheckObjectNodeImpl("ship1", "ship"),
        new ActionNodeImpl("ship1", "rotate", "1")
    );

    assertThatThrownBy(() -> instruction.interpret(ioc))
        .isInstanceOf(ControlPermissionCommandException.class);
  }

  @Test
  void givenPermissionWithoutObject_whenInterpret_thenExceptionThrownUpdated() {
    instruction = new InstructionImpl(
        new PermissionNodeImpl("player", "player1", "ship1", "accelerate"),
        new CheckObjectNodeImpl("ship2", "ship"),
        new ActionNodeImpl("ship1", "rotate", "1")
    );

    assertThatThrownBy(() -> instruction.interpret(ioc))
        .isInstanceOf(ObjectNotFoundCommandException.class);
  }
}