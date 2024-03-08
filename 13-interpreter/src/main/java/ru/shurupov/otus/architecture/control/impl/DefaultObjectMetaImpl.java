package ru.shurupov.otus.architecture.control.impl;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.control.ObjectMeta;

@RequiredArgsConstructor
public class DefaultObjectMetaImpl implements ObjectMeta {

  private final Map<String, Object> object;

  @Override
  public String getType() {
    return (String) object.get("type");
  }
}
