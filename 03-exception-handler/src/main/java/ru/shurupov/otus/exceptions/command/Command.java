package ru.shurupov.otus.exceptions.command;


import ru.shurupov.otus.exceptions.exception.BaseException;

public interface Command {
  void execute() throws BaseException;
}
