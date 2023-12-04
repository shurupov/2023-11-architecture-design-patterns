package ru.shurupov.otus.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.exceptions.command.Command;
import ru.shurupov.otus.exceptions.exception.BaseException;

@Slf4j
public class HandlerSelector {

  private final Map<Class<? extends BaseException>, Map<Class<? extends Command>, BiFunction<BaseException, Command, Command>>> handlerStore = new HashMap<>();

  @Setter
  private BiFunction<BaseException, Command, Command> defaultGenerator = (e, c) -> () -> log.info("Error");

  public void addHandler(Class<? extends BaseException> eClass, Class<? extends Command> cClass, BiFunction<BaseException, Command, Command> commandGenerator) {

    if (!handlerStore.containsKey(eClass)) {
      handlerStore.put(eClass, new HashMap<>());
    }

    handlerStore.get(eClass)
        .put(cClass, commandGenerator);
  }

  public Command getHandler(BaseException exception, Command command) {
    return handlerStore.getOrDefault(exception.getClass(), Map.of())
        .getOrDefault(command.getClass(), defaultGenerator)
        .apply(exception, command);
  }
}
