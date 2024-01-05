package ru.shurupov.otus.architecture.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.mdkt.compiler.InMemoryJavaCompiler;
import ru.shurupov.otus.architecture.generator.exception.ConstructorNotFoundException;

public class JavaCodeClassCompiler {
  public Class<?> compile(String javaCode, String className) throws Exception {
    return InMemoryJavaCompiler.newInstance().compile(className, javaCode);
  }

  public <C> C createInstance(Class<C> cls, Object ...args)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {

    for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
      if (constructor.getParameterCount() != args.length) {
        continue;
      }
      for (int i = 0; i < args.length; i++) {
        Class<?> parameterType = constructor.getParameterTypes()[i];
        if (!args[i].getClass().equals(parameterType) && !args[i].getClass().isAssignableFrom(parameterType)) {
          continue;
        }
        return (C) constructor.newInstance(args);
      }
    }

    throw new ConstructorNotFoundException();
  }
}
