package ru.shurupov.otus.command.command;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.command.exception.CommandException;

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
