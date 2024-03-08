package ru.shurupov.otus.architecture.interpreter.expression;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shurupov.otus.architecture.control.ControlAction;
import ru.shurupov.otus.architecture.interpreter.impl.AccelerateControlAction;
import ru.shurupov.otus.architecture.interpreter.impl.ShipAcceleratableAdapter;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class ControlActionNodeImplTest {

  private IoC ioc;
  private ActionNode actionNode;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

    ioc.resolve("IoC.Register", "Object.Add", (Function<Object[], Object>) (Object[] args) ->
        ioc.resolve("IoC.Register", args)
    );

    ioc.resolve("Object.Add", "ship1", new HashMap<>(Map.of(
        "type", "ship",
        "velocity", 0d
    )));

    ioc.resolve("Object.Add", "Object.Action", (Function<Object[], Object>) (Object[] args) -> {
      return ioc.resolve("Action." + args[0]);
    });

    ioc.resolve("Object.Add", "Object.Action.Add", (Function<Object[], Object>) (Object[] args) -> {
      return ioc.resolve("Object.Add", "Action." + args[0], args[1]);
    });

    ioc.resolve("Object.Add", "Accelerator.ship1", new ShipAcceleratableAdapter(ioc.resolve("ship1")));

    ioc.resolve("Object.Action.Add", "ship1", new AccelerateControlAction(ioc.resolve("Accelerator.ship1")));
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