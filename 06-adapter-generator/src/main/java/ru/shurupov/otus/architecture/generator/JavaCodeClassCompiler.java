package ru.shurupov.otus.architecture.generator;

import java.util.Map;
import org.mdkt.compiler.InMemoryJavaCompiler;
import ru.shurupov.otus.architecture.ioc.IoC;

public class JavaCodeClassCompiler {
  //TODO ioc и objectMap вынести отсюда
  public <I> I compile(String javaCode, String className, Class<I> i, IoC ioc, Map<String, Object> objectMap) throws Exception {

    Class<?> cls = InMemoryJavaCompiler.newInstance().compile(className, javaCode);

    return (I) cls.getDeclaredConstructor(IoC.class, Map.class).newInstance(ioc, objectMap);
  }
}
