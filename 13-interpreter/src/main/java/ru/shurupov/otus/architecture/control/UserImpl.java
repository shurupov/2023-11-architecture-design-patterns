package ru.shurupov.otus.architecture.control;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
public class UserImpl implements User {

  String type;
  Map<String, List<String>> permissions;
}
