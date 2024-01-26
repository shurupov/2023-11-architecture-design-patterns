package ru.shurupov.otus.architecture.mq.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.mq.model.GameMessage;

@SpringBootTest
public class MessageProcessingIntegrationTest {

  @Autowired
  private TestRabbitTemplate template;

  @Autowired
  private IoC ioc;

  @Test
  public void givenMessageShip1_whenSentToRabbit_thenProcessed() {

    ioc.resolve("Scope.Select", "MainGame");
    Map<String, Object> spaceship = ioc.resolve("Object.Spaceship1");
    ioc.resolve("Scope.Select", "root");

    template.convertAndSend("game",
        GameMessage.builder()
            .gameId("MainGame")
            .operation("Move")
            .objectId("Object.Spaceship1")
            .args(List.of())
            .build()
    );

    await()
        .until(() -> ((Double) spaceship.get("x")) > 100);

    assertThat(spaceship.get("x")).isEqualTo(110d);
    assertThat(spaceship.get("y")).isEqualTo(108d);
  }

  @Test
  public void givenMessageShip2_whenSentToRabbit_thenProcessed() {

    ioc.resolve("Scope.Select", "MainGame");
    Map<String, Object> spaceship = ioc.resolve("Object.Spaceship2");
    ioc.resolve("Scope.Select", "root");

    template.convertAndSend("game",
        GameMessage.builder()
            .gameId("MainGame")
            .operation("Move")
            .objectId("Object.Spaceship2")
            .args(List.of())
            .build()
    );

    await()
        .until(() -> ((Double) spaceship.get("x")) > 150);

    assertThat(spaceship.get("x")).isEqualTo(155d);
    assertThat(spaceship.get("y")).isEqualTo(154d);
  }

  @Test
  public void givenMessageWithWrongGameId_whenSentToRabbit_thenFailed() {

    ioc.resolve("Scope.Select", "root");

    assertThatThrownBy(() -> template.convertAndSend("game",
        GameMessage.builder()
            .gameId("WrongGame")
            .operation("Move")
            .objectId("Object.Spaceship2")
            .args(List.of())
            .build()
    )).isInstanceOf(ListenerExecutionFailedException.class);
  }

  @Test
  public void givenMessageWithWrongObjectId_whenSentToRabbit_thenFailed() {

    ioc.resolve("Scope.Select", "MainGame");

    Integer oldMoveExceptionCount = ioc.resolve("Counter.MoveException");
    assertThat(oldMoveExceptionCount).isEqualTo(0);

    template.convertAndSend("game",
        GameMessage.builder()
            .gameId("MainGame")
            .operation("Move")
            .objectId("Object.Spaceship3")
            .args(List.of())
            .build()
    );

    await()
        .until(() -> {
          ioc.resolve("Scope.Select", "MainGame");
          return ioc.resolve("Counter.MoveException").equals(1);
        });

    Integer newMoveExceptionCount = ioc.resolve("Counter.MoveException");
    assertThat(newMoveExceptionCount).isEqualTo(1);
  }

}
