package ru.shurupov.otus.architecture.interpreter.expression;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shurupov.otus.architecture.interpreter.util.CommonContextPreparation.prepareCommon;
import static ru.shurupov.otus.architecture.interpreter.util.ControlActionNodePreparation.prepareControlActionNode;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shurupov.otus.architecture.control.ControlAction;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class ControlActionNodeImplTest {

  private IoC ioc;
  private ActionNode actionNode;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    prepareCommon(ioc);
    prepareControlActionNode(ioc);
  }

  @Test
  void givenActionNode_whenInterpret_thenCorrectControlActionReturned() {
    actionNode = new ActionNodeImpl("ship1", "accelerate", "1");

    ControlAction controlAction = actionNode.interpret(ioc);

    controlAction.apply("10");

    Map<String, Object> ship = ioc.resolve("ship1");

    assertThat(ship.get("velocity")).isEqualTo(10d);

    controlAction.apply("-15");

    assertThat(ship.get("velocity")).isEqualTo(-5d);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "someParameter"})
  void givenActionNodeWithActionParameter_whenGetActionParameter_thenParameterReturned(String parameterValue) {
    actionNode = new ActionNodeImpl("ship1", "accelerate", parameterValue);
    assertThat(actionNode.getActionParameter()).isEqualTo(parameterValue);
  }
}