package ru.shurupov.otus.architecture.interpreter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.control.User;
import ru.shurupov.otus.architecture.interpreter.exception.InstructionParseException;
import ru.shurupov.otus.architecture.interpreter.expression.Instruction;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class InstructionParserTest {

  private IoC ioc = IoCFactory.simple();

  private InstructionParser parser;

  @BeforeEach
  public void init() {


    parser = new InstructionParser();
  }

  @Test
  void parse1() {
    assertThatThrownBy(() -> parser.parse("user 12345"))
        .isInstanceOf(InstructionParseException.class);
  }

  @Test
  @Disabled
  void parse2() {
    Instruction instruction = parser.parse("user 12345 accelerates 2 object 5");
    assertThat(instruction)
        .isNotNull();
    instruction.interpret(ioc);
  }
}