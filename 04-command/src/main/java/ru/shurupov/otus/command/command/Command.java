package ru.shurupov.otus.command.command;

import ru.shurupov.otus.command.exception.CommandException;

public interface Command {
  void execute() throws CommandException;
}
