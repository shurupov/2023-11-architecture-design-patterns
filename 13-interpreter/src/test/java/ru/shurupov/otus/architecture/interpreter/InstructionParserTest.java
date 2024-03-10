package ru.shurupov.otus.architecture.interpreter;

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
import ru.shurupov.otus.architecture.interpreter.exception.InstructionParseException;
import ru.shurupov.otus.architecture.interpreter.expression.Instruction;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class InstructionParserTest {

  private IoC ioc;

  private InstructionParser parser;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    prepareCommon(ioc);
    preparePermissionNode(ioc);
    prepareCheckObjectNode(ioc);
    prepareControlActionNode(ioc);

    parser = new InstructionParser();
  }

  @Test
  void givenWrongDslInstruction_whenParse_thenExceptionThrown() {
    assertThatThrownBy(() -> parser.parse("user 12345"))
        .isInstanceOf(InstructionParseException.class);
  }

  @Test
  void givenCorrectDslInstruction_whenParse_thenCreatedCorrectInstruction() {
    Instruction instruction = parser.parse("player player1 accelerate 1 ship ship1");

    Command command = instruction.interpret(ioc);

    command.execute();

    Map<String, Object> ship = ioc.resolve("ship1");
    assertThat(ship.get("velocity")).isEqualTo(1d);
  }
}