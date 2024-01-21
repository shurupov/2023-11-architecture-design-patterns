package ru.shurupov.otus.architecture.generator;

import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.abstraction.activity.Movable;
import ru.shurupov.otus.architecture.abstraction.entity.Position;
import ru.shurupov.otus.architecture.abstraction.entity.Velocity;
import ru.shurupov.otus.architecture.generator.ClassStructure.FieldTemplate;
import ru.shurupov.otus.architecture.generator.exception.UnableToGenerateInstanceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.IoCFactory;

import static org.assertj.core.api.Assertions.assertThat;

class AdapterGeneratorGenerateMovableIntegrationTest {

  private IoC ioc;

  @BeforeEach
  public void init() {
    ioc = IoCFactory.simple();

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

    ioc.resolve("IoC.Register", "Adapter.Create", (Function<Object[], ?>) (args) -> {
      AdapterGenerator adapterGenerator = ioc.resolve("Generator");
      try {
        return adapterGenerator.generate((Class<?>) args[0], ioc, args[1]);
      } catch (Exception e) {
        throw new UnableToGenerateInstanceException(e);
      }
    });
    
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

    ioc.resolve("IoC.Register", "spaceship", new HashMap<>(Map.of(
        "x", 100d,
        "y", 101d,
        "speedX", 5d,
        "speedY", 6d
    )));
  }

  @Test
  void givenMovableAdapter_whenGetPosition_thenPositionReturned() {

    Object object = ioc.resolve("spaceship");

    Movable spaceShipMovableAdapter = ioc.resolve("Adapter.Create", Movable.class, object);

    Position position = spaceShipMovableAdapter.getPosition();

    assertThat(position)
        .isNotNull();
    assertThat(position.getCoords())
        .hasSize(2)
        .usingComparatorWithPrecision(0.0001)
        .containsExactly(100d, 101d);
  }

  @Test
  void givenMovableAdapter_whenGetVelocity_thenVelocityReturned() {

    Object object = ioc.resolve("spaceship");

    Movable spaceShipMovableAdapter = ioc.resolve("Adapter.Create", Movable.class, object);

    Velocity velocity = spaceShipMovableAdapter.getVelocity();

    assertThat(velocity)
        .isNotNull();
    assertThat(velocity.getPositionDelta())
        .hasSize(2)
        .usingComparatorWithPrecision(0.0001)
        .containsExactly(5d, 6d);
  }

  @Test
  void givenMovableAdapter_whenMoveWithVelocity_thenPositionChanged() {

    Object object = ioc.resolve("spaceship");

    Movable spaceShipMovableAdapter = ioc.resolve("Adapter.Create", Movable.class, object);

    spaceShipMovableAdapter.move(spaceShipMovableAdapter.getVelocity());

    Position position = spaceShipMovableAdapter.getPosition();

    assertThat(position)
        .isNotNull();
    assertThat(position.getCoords())
        .hasSize(2)
        .usingComparatorWithPrecision(0.0001)
        .containsExactly(105d, 107d);
  }
}