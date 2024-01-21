package ru.shurupov.otus.architecture.mq.server.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shurupov.otus.architecture.abstraction.activity.Movable;
import ru.shurupov.otus.architecture.abstraction.entity.Position;
import ru.shurupov.otus.architecture.abstraction.entity.Velocity;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.command.MoveCommand;
import ru.shurupov.otus.architecture.concurrent.command.StartCommand;
import ru.shurupov.otus.architecture.concurrent.executor.EventLoopStarter;
import ru.shurupov.otus.architecture.exception.HandlerSelector;
import ru.shurupov.otus.architecture.exception.MoveCommandException;
import ru.shurupov.otus.architecture.generator.AdapterGenerator;
import ru.shurupov.otus.architecture.generator.ClassStructure.FieldTemplate;
import ru.shurupov.otus.architecture.generator.ClassStructureCollector;
import ru.shurupov.otus.architecture.generator.IocResolveMethodBodyGenerator;
import ru.shurupov.otus.architecture.generator.JavaCodeClassCompiler;
import ru.shurupov.otus.architecture.generator.JavaCodeClassGenerator;
import ru.shurupov.otus.architecture.generator.exception.UnableToGenerateInstanceException;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

@Configuration
public class IoCConfig {

  @Bean
  public IoC ioc() {
    IoC ioc = IoCFactory.scoped();

    addObjectImplementationGenerator(ioc);
    addAdapterCreator(ioc);
    addMovableMethodsGenerator(ioc);
    addMoveCommandCreator(ioc);
    addAndSetScope(ioc, "MainGame");
    addSpaceShip(ioc, "Object.Spaceship1", 100, 99, 10, 9);
    addSpaceShip(ioc, "Object.Spaceship2", 150, 149, 5, 5);
    createAndStartEventLoop(ioc);
    addMoveExceptionHandler(ioc);

    return ioc;
  }

  private void addMoveExceptionHandler(IoC ioc) {
    ioc.resolve("IoC.Register", "Counter.MoveException", 0);
    HandlerSelector selector = ioc.resolve("Event.Loop.ExceptionSelector");
    selector.addHandler(MoveCommandException.class, MoveCommand.class, (e, c) -> () -> {
      Integer counter = ioc.resolve("Counter.MoveException");
      counter++;
      ioc.resolve("IoC.Register", "Counter.MoveException", counter);
    });
  }

  private void addAndSetScope(IoC ioc, String scopeName) {
    ioc.resolve("Scope.Add", scopeName);
    ioc.resolve("Scope.Select", scopeName);
  }

  private void createAndStartEventLoop(IoC ioc) {
    BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
    HandlerSelector handlerSelector = new HandlerSelector();
    EventLoopStarter eventLoopStarter = new EventLoopStarter(queue, handlerSelector);
    Command startCommand = new StartCommand(eventLoopStarter);
    startCommand.execute();

    ioc.resolve("IoC.Register", "Event.Loop.Queue", queue);
    ioc.resolve("IoC.Register", "Event.Loop.ExceptionSelector", handlerSelector);
    ioc.resolve("IoC.Register", "Event.Loop.Starter", eventLoopStarter);
  }

  private void addMoveCommandCreator(IoC ioc) {
    ioc.resolve("IoC.Register", "Command.Create.Move", (Function<Object[], Command>) (args) -> {
      Map<String, Object> object = ioc.resolve((String) args[0]);
      Movable movableAdapter = ioc.resolve("Adapter.Create", Movable.class, object);
      return new MoveCommand(movableAdapter);
    });
  }

  private void addSpaceShip(IoC ioc, String name, double x, double y, double speedX, double speedY) {
    ioc.resolve("IoC.Register", name, new HashMap<>(Map.of(
        "x", x,
        "y", y,
        "speedX", speedX,
        "speedY", speedY
    )));
  }

  private void addMovableMethodsGenerator(IoC ioc) {
    ioc.resolve("IoC.Register", "Movable.Position.Get", (Function<Object[], Position>) (args) -> {
      Map<String, Object> object = (Map<String, Object>) args[0];
      return () -> new double[] {(Double) object.get("x"), (Double) object.get("y")};
    });

    ioc.resolve("IoC.Register", "Movable.Move", (Function<Object[], Boolean>) (args) -> {
      Map<String, Object> object = (Map<String, Object>) args[0];
      Velocity velocity = (Velocity) args[1];
      object.put("x", (Double) object.get("x") + velocity.getPositionDelta()[0]);
      object.put("y", (Double) object.get("y") + velocity.getPositionDelta()[1]);
      return true;
    });

    ioc.resolve("IoC.Register", "Movable.Velocity.Get", (Function<Object[], Velocity>) (args) -> {
      Map<String, Object> object = (Map<String, Object>) args[0];
      return () -> new double[] {(Double) object.get("speedX"), (Double) object.get("speedY")};
    });
  }

  private void addAdapterCreator(IoC ioc) {
    ioc.resolve("IoC.Register", "Adapter.Create", (Function<Object[], ?>) (args) -> {
      AdapterGenerator adapterGenerator = ioc.resolve("Generator");
      try {
        return adapterGenerator.generate((Class<?>) args[0], ioc, args[1]);
      } catch (Exception e) {
        throw new UnableToGenerateInstanceException(e);
      }
    });
  }

  private void addObjectImplementationGenerator(IoC ioc) {
    ioc.resolve("IoC.Register", "Generator.Collector",
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

    ioc.resolve("IoC.Register", "Generator",
        new AdapterGenerator(
            ioc.resolve("Generator.Collector"),
            new JavaCodeClassGenerator(),
            new JavaCodeClassCompiler()
        )
    );
  }
}
