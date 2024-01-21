package ru.shurupov.architecture.command;


import ru.shurupov.architecture.exception.BaseException;

public interface Command {
  void execute() throws BaseException;
}
