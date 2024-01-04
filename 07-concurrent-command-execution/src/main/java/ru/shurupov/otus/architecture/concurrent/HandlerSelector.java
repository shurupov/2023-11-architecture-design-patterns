package ru.shurupov.otus.architecture.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.concurrent.command.Command;
import ru.shurupov.otus.architecture.concurrent.exception.CommandException;

@Slf4j
public class HandlerSelector {

  private final Map<Class<? extends CommandException>, Map<Class<? extends Command>, BiFunction<CommandException, Command, Command>>> handlerStore = new HashMap<>();

  @Setter
  private BiFunction<CommandException, Command, Command> defaultGenerator = (e, c) -> () -> log.info("Error");

  public void addHandler(Class<? extends CommandException> eClass, Class<? extends Command> cClass, BiFunction<CommandException, Command, Command> commandGenerator) {

    if (!handlerStore.containsKey(eClass)) {
      handlerStore.put(eClass, new HashMap<>());
    }

    handlerStore.get(eClass)
        .put(cClass, commandGenerator);
  }

  public Command getHandler(CommandException exception, Command command) {
    return handlerStore.getOrDefault(exception.getClass(), Map.of())
        .getOrDefault(command.getClass(), defaultGenerator)
        .apply(exception, command);
  }
}
