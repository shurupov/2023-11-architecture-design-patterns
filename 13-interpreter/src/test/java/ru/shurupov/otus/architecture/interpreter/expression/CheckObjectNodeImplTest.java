package ru.shurupov.otus.architecture.interpreter.expression;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.control.ObjectMeta;
import ru.shurupov.otus.architecture.control.User;
import ru.shurupov.otus.architecture.control.impl.DefaultObjectMetaImpl;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

class CheckObjectNodeImplTest {

  private IoC ioc;

  private CheckObjectNode checkObjectNode;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();
    ioc.resolve("IoC.Register", "Object.Add", (Function<Object[], Object>) (Object[] args) ->
        ioc.resolve("IoC.Register", args)
    );

    ioc.resolve("Object.Add", "Control.Meta.AddFrom", (Function<Object[], Object>) (Object[] args) -> {
      String id = (String) args[0];
      ObjectMeta meta = new DefaultObjectMetaImpl(ioc.resolve(id));
      return ioc.resolve("Object.Add", "Meta." + id, meta);
    });

    ioc.resolve("Object.Add", "Object.Meta.Get",
        (Function<Object[], ObjectMeta>) (Object[] args) ->
            ioc.resolve("Meta." + (String) args[0])
    );

    ioc.resolve("Object.Add", "ship1", new HashMap<>(Map.of("type", "ship")));
    ioc.resolve("Control.Meta.AddFrom", "ship1");

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