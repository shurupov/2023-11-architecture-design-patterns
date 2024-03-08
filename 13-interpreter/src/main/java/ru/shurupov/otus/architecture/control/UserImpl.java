package ru.shurupov.otus.architecture.control;

import java.util.List;
import java.util.Map;

public class UserImpl implements User {

  private final Map<String, Object> object;

  public UserImpl(Map<String, Object> object) {
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
