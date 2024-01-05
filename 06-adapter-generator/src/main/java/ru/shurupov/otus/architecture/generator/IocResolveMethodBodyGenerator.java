package ru.shurupov.otus.architecture.generator;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class IocResolveMethodBodyGenerator implements MethodBodyGenerator {

  private final String iocFieldName;
  private final String iocResolveMethodName;
  private final String objectFieldName;
  private final int lineOffset;

  @Override
  public String generate(boolean returnsVoid, String responsibility, String entity, String action, List<String> parameters) {
    StringBuilder sb = new StringBuilder();

    sb.append(StringUtils.repeat(' ', lineOffset));

    if (!returnsVoid) {
      sb.append("return ");
    }

    sb.append(iocFieldName)
        .append('.')
        .append(iocResolveMethodName)
        .append('(');

    sb.append('\"');
    sb.append(responsibility);

    if (StringUtils.isNoneBlank(entity)) {
      sb.append('.').append(entity);
    }

    sb.append('.').append(action);

    sb.append("\", ")
        .append(objectFieldName);

    for (String parameter : parameters) {
      sb.append(", ").append(parameter);
    }

    sb.append(");\n");

    return sb.toString();
  }

}
