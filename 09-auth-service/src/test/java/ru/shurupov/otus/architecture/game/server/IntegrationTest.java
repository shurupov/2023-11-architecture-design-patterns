package ru.shurupov.otus.architecture.game.server;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.response.AddCommandResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.CreateGameResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.JoinGameResponse;
import ru.shurupov.otus.architecture.game.server.handler.RequestHandler;
import ru.shurupov.otus.architecture.game.server.service.GameServiceMapSetImpl;

public class IntegrationTest {

  private HttpClient httpClient;
  private Gson gson;
  private String jwtSecret;

  @BeforeEach
  public void init() {
    jwtSecret = "gameserverjwttokensecretgameserverjwttokensecretgameserverjwttokensecretgameserverjwttokensecret";

    gson = new Gson();

    new RequestHandler(
        new GameServiceMapSetImpl(),
        jwtSecret,
        120
    ).init();

    httpClient = HttpClient
        .newBuilder()
        .proxy(ProxySelector.getDefault())
        .build();
  }

  @Test
  public void givenServer_whenCreateGameJoinSendMessage_thenItWorks() throws URISyntaxException, IOException, InterruptedException {
    HttpRequest createGameRequest = HttpRequest.newBuilder()
        .uri(new URI("http://localhost:4567/create"))
        .POST(HttpRequest.BodyPublishers.ofString("{\"participants\": [ \"SuperMario\", \"SuperLuigi\" ]}"))
        .build();

    HttpResponse<String> createGameResponse = httpClient.send(createGameRequest, BodyHandlers.ofString());

    assertThat(createGameResponse.statusCode()).isEqualTo(200);

    CreateGameResponse createGameResponseParsed = gson.fromJson(createGameResponse.body(), CreateGameResponse.class);
    String gameId = createGameResponseParsed.getGameId();

    assertThat(gameId).isNotNull();

    HttpRequest joinGameRequest = HttpRequest.newBuilder()
        .uri(new URI("http://localhost:4567/join"))
        .POST(HttpRequest.BodyPublishers.ofString("{\"gameId\": \"" + gameId + "\", \"username\": \"SuperMario\"}"))
        .build();

    HttpResponse<String> joinGameResponse = httpClient.send(joinGameRequest, BodyHandlers.ofString());

    assertThat(joinGameResponse.statusCode()).isEqualTo(200);

    JoinGameResponse joinGameResponseParsed = gson.fromJson(joinGameResponse.body(), JoinGameResponse.class);

    String token = joinGameResponseParsed.getToken();

    assertThat(token).isNotNull();

    Jws<Claims> jwt = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(token);

    assertThat(jwt.getBody().getSubject()).isEqualTo("SuperMario");
    assertThat(jwt.getBody().get("gameId")).isEqualTo(gameId);

    String payload = RandomStringUtils.random(50, true, true);

    HttpRequest addCommandRequest = HttpRequest.newBuilder()
        .uri(new URI("http://localhost:4567/commands"))
        .header("x-api-token", token)
        .POST(HttpRequest.BodyPublishers.ofString("{\"payload\": \"" + payload + "\"}"))
        .build();

    HttpResponse<String> addCommandResponse = httpClient.send(addCommandRequest, BodyHandlers.ofString());

    assertThat(addCommandResponse.statusCode()).isEqualTo(200);

    AddCommandResponse addCommandResponseParsed = gson.fromJson(addCommandResponse.body(), AddCommandResponse.class);

    assertThat(addCommandResponseParsed.getMessage()).isEqualTo(String.format("Added command %s for user SuperMario", new GameCommandMessage(payload)));
  }

}
