package ru.shurupov.otus.architecture.generator;

import java.util.List;

public interface MethodBodyGenerator {
  String generate(boolean returnsVoid, String responsibility, String entity, String action, List<String> parameters);
}
