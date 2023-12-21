package ru.shurupov.otus.architecture;

import java.util.function.Function;
import ru.shurupov.otus.architecture.activity.actor.Movable;
import ru.shurupov.otus.architecture.activity.entity.Position;
import ru.shurupov.otus.architecture.activity.entity.Velocity;
import ru.shurupov.otus.architecture.generator.*;
import ru.shurupov.otus.architecture.generator.ClassStructure.FieldTemplate;
import ru.shurupov.otus.architecture.generator.exception.UnableToGenerateInstanceException;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.strategy.IoCFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args0) throws Exception {
      IoC ioc = IoCFactory.simple();

      ioc.resolve("IoC.Register", "fromMapAdapterStructureCollector",
          new ClassStructureCollector(
              List.of(
                  FieldTemplate.builder()
                      .canonicalType("ru.shurupov.otus.architecture.ioc.IoC")
                      .type("IoC")
                      .name("ioc")
                      .build(),

                  FieldTemplate.builder()
                      .canonicalType("java.util.Map")
                      .type("Map<String, Object>")
                      .name("object")
                      .build()
              ),
              new IocResolveMethodBodyGenerator(
                  "ioc",
                  "resolve",
                  "object",
                  4
              )
          )
      );

      ioc.resolve("IoC.Register", "fromMapAdapterGenerator",
          new AdapterGenerator(
              ioc.resolve("fromMapAdapterStructureCollector"),
              new JavaCodeClassGenerator(),
              new JavaCodeClassCompiler()
          )
      );

      ioc.resolve("IoC.Register", "createMovable", (Function<Object[], Movable>) (args) -> {
        AdapterGenerator adapterGenerator = ioc.resolve("fromMapAdapterGenerator");
        try {
          return adapterGenerator.generate(Movable.class, ioc, args[0]);
        } catch (Exception e) {
          throw new UnableToGenerateInstanceException(e);
        }
      });

      ioc.resolve("Ioc.Register", "Movable.Position.Get", (Function<Object[], Position>) (args) -> {
        Map<String, Object> object = (Map<String, Object>) args[0];
        return () -> new double[] {(Double) object.get("x"), (Double) object.get("y")};
      });

      ioc.resolve("Ioc.Register", "Movable.Move", (Function<Object[], Boolean>) (args) -> {
        Map<String, Object> object = (Map<String, Object>) args[0];
        Velocity velocity = (Velocity) args[1];
        object.put("x", (Double) object.get("x") + velocity.getPositionDelta()[0]);
        object.put("y", (Double) object.get("y") + velocity.getPositionDelta()[1]);
        return true;
      });

      ioc.resolve("Ioc.Register", "Movable.Velocity.Get", (Function<Object[], Velocity>) (args) -> {
        Map<String, Object> object = (Map<String, Object>) args[0];
        return () -> new double[] {(Double) object.get("speedX"), (Double) object.get("speedY")};
      });

      ioc.resolve("IoC.Register", "spaceship", new HashMap<>(Map.of(
          "x", 100d,
          "y", 101d,
          "speedX", 5d,
          "speedY", 6d
      )));

      /*Movable spaceShipMovableAdapter = */ioc.resolve("createMovable", ioc.resolve("spaceship"));
    }
}
