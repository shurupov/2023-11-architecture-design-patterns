package ru.shurupov.architecture.command;


import ru.shurupov.architecture.exception.CommandException;

public interface Command {
  void execute() throws CommandException;
}
