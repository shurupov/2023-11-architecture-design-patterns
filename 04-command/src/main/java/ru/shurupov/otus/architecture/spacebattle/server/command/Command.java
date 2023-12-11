package ru.shurupov.otus.architecture.spacebattle.server.command;

import ru.shurupov.otus.architecture.spacebattle.server.exception.CommandException;

public interface Command {
  void execute() throws CommandException;
}
