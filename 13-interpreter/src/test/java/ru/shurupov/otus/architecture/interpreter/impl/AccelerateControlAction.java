package ru.shurupov.otus.architecture.interpreter.impl;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.control.ControlAction;
import ru.shurupov.otus.architecture.interpreter.abstraction.Acceleratable;

@RequiredArgsConstructor
public class AccelerateControlAction implements ControlAction {

  private final Acceleratable acceleratable;

  @Override
  public void apply(String parameter) {
    acceleratable.accelerate(Double.parseDouble(parameter));
  }
}
