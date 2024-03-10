package ru.shurupov.otus.architecture.interpreter.util;

import java.util.function.Function;
import ru.shurupov.otus.architecture.interpreter.impl.AccelerateControlAction;
import ru.shurupov.otus.architecture.interpreter.impl.ShipAcceleratableAdapter;
import ru.shurupov.otus.architecture.ioc.IoC;

public class ControlActionNodePreparation {

  public static void prepareControlActionNode(IoC ioc) {
    ioc.resolve("Object.Add", "Object.Action", (Function<Object[], Object>) (Object[] args) -> {
      return ioc.resolve("Action." + args[0]);
    });

    ioc.resolve("Object.Add", "Object.Action.Add", (Function<Object[], Object>) (Object[] args) -> {
      return ioc.resolve("Object.Add", "Action." + args[0], args[1]);
    });

    ioc.resolve("Object.Add", "Accelerator.ship1", new ShipAcceleratableAdapter(ioc.resolve("ship1")));

    ioc.resolve("Object.Action.Add", "ship1", new AccelerateControlAction(ioc.resolve("Accelerator.ship1")));
  }
}
