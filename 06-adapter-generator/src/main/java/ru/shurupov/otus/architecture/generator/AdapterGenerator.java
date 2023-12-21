package ru.shurupov.otus.architecture.generator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdapterGenerator {

  private final ClassStructureCollector collector;
  private final JavaCodeClassGenerator codeGenerator;
  private final JavaCodeClassCompiler codeCompiler;

  public <I> I generate(Class<I> i, Object ...args) throws Exception {
    ClassStructure structure = collector.collect(i);
    String javaCode = codeGenerator.generateJava(structure);
    Class<?> cls = codeCompiler.compile(
        javaCode,
        structure.getPackageName() + "." + structure.getClassName()
    );
    return (I) codeCompiler.createInstance(cls, args);
  }
}
