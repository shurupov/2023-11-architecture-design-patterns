package ru.shurupov.otus.architecture.interpreter.expression;

import lombok.Value;
import ru.shurupov.otus.architecture.control.User;
import ru.shurupov.otus.architecture.ioc.IoC;

@Value
public class PermissionNodeImpl implements PermissionNode {

  String userType;
  String userId;
  String objectId;
  String actionName;

  @Override
  public Boolean interpret(IoC ioc) {
    User user = ioc.resolve("User.Get", userId);
    if (user == null) {
      return false;
    }
    if (!userType.equals(user.getType())) {
      return false;
    }
    if (!user.getPermissions().containsKey(objectId)) {
      return false;
    }
    return user.getPermissions().get(objectId).contains(actionName);
  }
}
