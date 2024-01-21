package ru.shurupov.otus.architecture.exception.handler.generator;

import java.util.function.BiFunction;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.exception.CommandException;

@Slf4j
public class DefaultHandlerGenerator implements BiFunction<CommandException, Command, Command> {

  @Override
  public Command apply(CommandException exception, Command command) {
    return () -> log.info("Command {} threw exception {}", command, exception);
  }
}
