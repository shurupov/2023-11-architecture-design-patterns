package ru.shurupov.otus.architecture;

import ru.shurupov.otus.architecture.activity.actor.Movable;
import ru.shurupov.otus.architecture.generator.*;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.ioc.strategy.IoCFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {
        AdapterGenerator adapterGenerator = new AdapterGenerator(
                new ClassStructureCollector(
                        List.of(
                                ClassStructure.FieldTemplate.builder()
                                        .canonicalType("ru.shurupov.otus.architecture.ioc.IoC")
                                        .type("IoC")
                                        .name("ioc")
                                        .build(),

                                ClassStructure.FieldTemplate.builder()
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

        Map<String, Object> object = new HashMap<>();

        IoC ioc = IoCFactory.simple();

        Movable movable = adapterGenerator.generate(Movable.class, ioc, object);
    }
}
