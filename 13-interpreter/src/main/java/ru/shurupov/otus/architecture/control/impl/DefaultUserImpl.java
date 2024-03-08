package ru.shurupov.otus.architecture.control.impl;

import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.control.User;

public class DefaultUserImpl implements User {

  private final Map<String, Object> object;

  public DefaultUserImpl(Map<String, Object> object) {
    this.object = object;
  }

  @Override
  public String getType() {
    return (String) object.get("type");
  }

  @Override
  public Map<String, List<String>> getPermissions() {
    return (Map<String, List<String>>) object.get("permissions");
  }
}
