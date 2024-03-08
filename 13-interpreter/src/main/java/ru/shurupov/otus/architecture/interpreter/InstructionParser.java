package ru.shurupov.otus.architecture.interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.shurupov.otus.architecture.interpreter.exception.InstructionParseException;
import ru.shurupov.otus.architecture.interpreter.expression.ActionNodeImpl;
import ru.shurupov.otus.architecture.interpreter.expression.CheckObjectNodeImpl;
import ru.shurupov.otus.architecture.interpreter.expression.Instruction;
import ru.shurupov.otus.architecture.interpreter.expression.PermissionNodeImpl;
import ru.shurupov.otus.architecture.interpreter.expression.InstructionImpl;

public class InstructionParser {

  private static final int SUBJECT_TYPE_INDEX = 1;
  private static final int SUBJECT_ID_INDEX = 2;
  private static final int ACTION_NAME_INDEX = 3;
  private static final int ACTION_PARAMETER_INDEX = 4;
  private static final int OBJECT_TYPE_INDEX = 5;
  private static final int OBJECT_ID_INDEX = 6;

  private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("\\s*(\\w+)\s+([\\d\\w]+)\\s+(\\w+)\s+([\\d\\w]+)\\s+(\\w+)\\s+([\\d\\w]+)\\s*");

  /*
  * instruction example: user 12345 accelerates 2 object 5
  * */
  public Instruction parse(String dslInstruction) {

    Matcher matcher = INSTRUCTION_PATTERN.matcher(dslInstruction);
    if (matcher.matches()) {
      return InstructionImpl.builder()
          .permissionNode(
              new PermissionNodeImpl(
                  matcher.group(SUBJECT_TYPE_INDEX),
                  matcher.group(SUBJECT_ID_INDEX),
                  matcher.group(OBJECT_ID_INDEX),
                  matcher.group(ACTION_NAME_INDEX)
              )
          )
          .checkObjectNode(
              new CheckObjectNodeImpl(
                  matcher.group(OBJECT_ID_INDEX),
                  matcher.group(OBJECT_TYPE_INDEX)
              )
          )
          .actionNode(
              new ActionNodeImpl(
                  matcher.group(OBJECT_ID_INDEX),
                  matcher.group(ACTION_NAME_INDEX),
                  matcher.group(ACTION_PARAMETER_INDEX)
              )
          )
          .build();
    }

    throw new InstructionParseException();
  }
}
