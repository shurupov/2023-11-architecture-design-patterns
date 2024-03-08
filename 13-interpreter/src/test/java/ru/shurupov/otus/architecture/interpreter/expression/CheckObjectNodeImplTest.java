package ru.shurupov.otus.architecture.interpreter.expression;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shurupov.otus.architecture.interpreter.util.CheckObjectNodePreparation.prepareCheckObjectNode;
import static ru.shurupov.otus.architecture.interpreter.util.CommonContextPreparation.prepareCommon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class CheckObjectNodeImplTest {

  private IoC ioc;

  private CheckObjectNode checkObjectNode;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    prepareCommon(ioc);
    prepareCheckObjectNode(ioc);
  }

  @Test
  public void givenObject_whenInterpret_thenTrue() {
    checkObjectNode = new CheckObjectNodeImpl("ship1", "ship");

    assertThat(checkObjectNode.interpret(ioc)).isTrue();
  }

  @Test
  public void givenObjectOfAnotherType_whenInterpret_thenFalse() {
    checkObjectNode = new CheckObjectNodeImpl("ship1", "human");

    assertThat(checkObjectNode.interpret(ioc)).isFalse();
  }

  @Test
  public void givenObjectWithAnotherId_whenInterpret_thenFalse() {
    checkObjectNode = new CheckObjectNodeImpl("ship2", "ship");

    assertThat(checkObjectNode.interpret(ioc)).isFalse();
  }
}