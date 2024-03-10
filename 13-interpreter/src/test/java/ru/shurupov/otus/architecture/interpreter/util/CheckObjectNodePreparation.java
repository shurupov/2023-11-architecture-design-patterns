package ru.shurupov.otus.architecture.interpreter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import ru.shurupov.otus.architecture.control.ObjectMeta;
import ru.shurupov.otus.architecture.control.impl.DefaultObjectMetaImpl;
import ru.shurupov.otus.architecture.ioc.IoC;

public class CheckObjectNodePreparation {

  public static void prepareCheckObjectNode(IoC ioc) {
    ioc.resolve("Object.Add", "Control.Meta.AddFrom", (Function<Object[], Object>) (Object[] args) -> {
      String id = (String) args[0];
      ObjectMeta meta = new DefaultObjectMetaImpl(ioc.resolve(id));
      return ioc.resolve("Object.Add", "Meta." + id, meta);
    });

    ioc.resolve("Object.Add", "Object.Meta.Get",
        (Function<Object[], ObjectMeta>) (Object[] args) ->
            ioc.resolve("Meta." + (String) args[0])
    );

    ioc.resolve("Control.Meta.AddFrom", "ship1");
  }
}
