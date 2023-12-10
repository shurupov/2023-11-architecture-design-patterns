package ru.shurupov.otus.architecture.spacebattle.server.command;

import java.util.List;

public class RotateMacroCommand extends MacroCommand {

  public RotateMacroCommand(ModifyVelocityByRotationCommand modifyVelocityCommand, RotateCommand rotationCommand) {
    super(List.of(modifyVelocityCommand, rotationCommand));
  }
}
