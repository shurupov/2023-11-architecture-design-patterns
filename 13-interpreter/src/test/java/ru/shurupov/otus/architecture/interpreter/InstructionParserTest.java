package ru.shurupov.otus.architecture.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.interpreter.expression.AstNode;

class InstructionParserTest {

  private InstructionParser parser;

  @BeforeEach
  public void init() {
    parser = new InstructionParser();
  }

  @Test
  void parse1() {
    AstNode<?> parsed = parser.parse("user 12345");
  }

  @Test
  void parse2() {
    AstNode<?> parsed = parser.parse("user 12345 accelerates 2 object 5");
  }
}