package ru.shurupov.otus.architecture.command;


import ru.shurupov.otus.architecture.exception.CommandException;

public interface Command {
  void execute() throws CommandException;
}
