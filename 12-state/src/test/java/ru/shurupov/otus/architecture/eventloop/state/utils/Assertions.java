package ru.shurupov.otus.architecture.eventloop.state.utils;

import static org.mockito.ArgumentMatchers.argThat;

import org.apache.commons.lang3.reflect.FieldUtils;
import ru.shurupov.otus.architecture.eventloop.EventLoop;

public class Assertions {

  public static  <T> T argHasField(Class<T> cls, String fieldName, Object value) {
    return argThat(s -> {
      try {
        EventLoop foundValue = (EventLoop) FieldUtils.getField(cls, fieldName, true).get(s);
        return value.equals(foundValue);
      } catch (IllegalAccessException e) {
        return false;
      }
    });
  }
}
