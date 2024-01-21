package ru.shurupov.otus.architecture.command;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.shurupov.architecture.exception.CommandException;

@RequiredArgsConstructor
public class MacroCommand implements Command {

  private final List<Command> commands;

  @Override
  public void execute() throws CommandException {
    for (Command command : commands) {
      command.execute();
    }
  }
}
