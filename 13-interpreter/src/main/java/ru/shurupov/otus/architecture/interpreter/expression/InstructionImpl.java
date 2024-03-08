package ru.shurupov.otus.architecture.interpreter.expression;

import lombok.Builder;
import lombok.Value;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.interpreter.exception.ControlPermissionCommandException;
import ru.shurupov.otus.architecture.interpreter.exception.ObjectNotFoundCommandException;
import ru.shurupov.otus.architecture.control.ControlAction;
import ru.shurupov.otus.architecture.ioc.IoC;

@Builder
@Value
public class InstructionImpl implements Instruction {

  PermissionNode permissionNode;
  CheckObjectNode checkObjectNode;
  ActionNode actionNode;

  @Override
  public Command interpret(IoC ioc) {

    if (!permissionNode.interpret(ioc)) {
      throw new ControlPermissionCommandException();
    }

    if (!checkObjectNode.interpret(ioc)) {
      throw new ObjectNotFoundCommandException();
    }

    return new InstructionCommand(actionNode.interpret(ioc), actionNode.getActionParameter());
  }

  @Value
  public static class InstructionCommand implements Command {

    ControlAction controlAction;
    String actionParameter;

    @Override
    public void execute() throws CommandException {
      controlAction.apply(actionParameter);
    }
  }
}
