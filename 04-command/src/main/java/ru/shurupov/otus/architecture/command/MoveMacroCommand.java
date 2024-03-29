package ru.shurupov.otus.architecture.command;

import java.util.List;

public class MoveMacroCommand extends MacroCommand {

  public MoveMacroCommand(Command checkFuelCommand, Command burnFuelCommand, MoveCommand moveCommand) {
    super(List.of(checkFuelCommand, burnFuelCommand, moveCommand));
  }
}
