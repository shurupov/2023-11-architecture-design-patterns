package ru.shurupov.otus.architecture.interpreter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import ru.shurupov.otus.architecture.ioc.IoC;

public class CommonContextPreparation {

  public static void prepareCommon(IoC ioc) {
    ioc.resolve("IoC.Register", "Object.Add", (Function<Object[], Object>) (Object[] args) ->
        ioc.resolve("IoC.Register", args)
    );

    ioc.resolve("Object.Add", "ship1", new HashMap<>(Map.of(
        "type", "ship",
        "velocity", 0d
    )));
  }
}
