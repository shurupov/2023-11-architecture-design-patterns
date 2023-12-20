package ru.shurupov.otus.architecture.generator;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.activity.actor.Movable;
import ru.shurupov.otus.architecture.generator.ClassStructure.FieldTemplate;
import ru.shurupov.otus.architecture.ioc.strategy.IoCFactory;

class AdapterGeneratorTest {

  AdapterGenerator adapterGenerator = new AdapterGenerator(
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
      ),
      new JavaCodeClassGenerator(),
      new JavaCodeClassCompiler()
  );

  @Test
  void generate() throws Exception {
    Movable movable = adapterGenerator.generate(Movable.class, IoCFactory.simple(), new HashMap<>());
  }
}