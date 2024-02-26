package ru.shurupov.otus.architecture.collision;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GameAreaConfig {
  int gameAreaWidth;
  int gameAreaHeight;
  int areaWidth;
  int areaHeight;
}
