package ru.shurupov.architecture.exception.handler.generator;

import java.util.function.BiFunction;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.architecture.command.Command;
import ru.shurupov.architecture.exception.BaseException;

@Slf4j
public class DefaultHandlerGenerator implements BiFunction<BaseException, Command, Command> {

  @Override
  public Command apply(BaseException exception, Command command) {
    return () -> log.info("Command {} threw exception {}", command, exception);
  }
}
