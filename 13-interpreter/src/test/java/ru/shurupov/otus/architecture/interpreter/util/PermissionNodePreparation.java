package ru.shurupov.otus.architecture.interpreter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import ru.shurupov.otus.architecture.control.User;
import ru.shurupov.otus.architecture.control.impl.DefaultUserImpl;
import ru.shurupov.otus.architecture.ioc.IoC;

public class PermissionNodePreparation {

  public static void preparePermissionNode(IoC ioc) {
    Function<Object[], User> createUserAdapterFunction = (Object[] args) -> {
      String id = (String) args[0];
      Map<String, Object> userObject = ioc.resolve(id);
      return new DefaultUserImpl(userObject);
    };

    ioc.<Boolean>resolve("Object.Add", "User.CreateFromObject", createUserAdapterFunction);

    Function<Object[], User> createUserFunction = (Object[] args) -> {
      String id = (String) args[0];
      String type = (String) args[1];
      Map<String, List<String>> permissions = new HashMap<>();

      for (int i = 2; i < args.length && i+1 < args.length; i += 2) {
        String object = (String) args[i];
        String action = (String) args[i + 1];
        if (!permissions.containsKey(object)) {
          permissions.put(object, new ArrayList<>());
        }
        permissions.get(object).add(action);
      }

      Map<String, Object> user = new HashMap<>();

      ioc.resolve("Object.Add", id, user);

      user.put("type", type);
      user.put("permissions", permissions);

      User user1 = ioc.resolve("User.CreateFromObject", id);

      ioc.resolve("IoC.Register", id + "_User", user1);

      return user1;
    };

    ioc.<Boolean>resolve("IoC.Register", "User.Create", createUserFunction);

    BiFunction<Map<String, Object>, Object[], User> getUser = (Map<String, Object> context, Object[] args) -> {
      return (User) ioc.resolve(((String) args[0]) + "_User");
    };

    ioc.<Boolean>resolve("IoC.Register", "User.Get", getUser);

    ioc.resolve("User.Create", "player1", "player", "ship1", "accelerate", "ship1", "rotate", "ship3", "accelerate");
  }

}
