package ru.shurupov.otus.architecture.control;

import java.util.List;
import java.util.Map;

public interface User extends ObjectMeta {

  String getType();

  Map<String, List<String>> getPermissions();
}
