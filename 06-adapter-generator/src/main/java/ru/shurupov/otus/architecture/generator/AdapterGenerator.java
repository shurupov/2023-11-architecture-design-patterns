package ru.shurupov.otus.architecture.generator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.ioc.IoC;

@RequiredArgsConstructor
public class AdapterGenerator {

  private final ClassStructureCollector collector;
  private final JavaCodeClassGenerator codeGenerator;
  private final JavaCodeClassCompiler codeCompiler;

  public <I> I generate(Class<I> i, IoC ioc, Map<String, Object> objectMap)
      throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ClassStructure structure = collector.collect(i);
    String javaCode = codeGenerator.generateJava(structure);
    return codeCompiler.compile(javaCode, i, ioc, objectMap);
  }
}
