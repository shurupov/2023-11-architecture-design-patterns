package ru.shurupov.otus.architecture.command;

import ru.shurupov.architecture.exception.CommandException;

public interface Command {
  void execute() throws CommandException;
}
