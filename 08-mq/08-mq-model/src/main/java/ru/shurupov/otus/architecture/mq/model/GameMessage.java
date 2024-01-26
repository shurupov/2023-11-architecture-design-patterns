package ru.shurupov.otus.architecture.mq.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameMessage implements Serializable {
  private String gameId;
  private String objectId;
  private String operation;
  private List<Object> args;
}
