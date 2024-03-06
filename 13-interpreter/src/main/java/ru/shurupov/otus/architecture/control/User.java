package ru.shurupov.otus.architecture.control;

import java.util.List;
import java.util.Map;

public interface User {

  String getType();

  Map<String, List<String>> getPermissions();
}
