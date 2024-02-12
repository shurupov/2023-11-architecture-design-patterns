package ru.shurupov.otus.architecture.game.server.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;

class JwtServiceTest {


  private String jwtSecret;
  private long tokenTtlMinutes;

  private JwtService jwtService;

  @BeforeEach
  public void init() {
    jwtSecret = RandomStringUtils.random(550, true, true);
    tokenTtlMinutes = 5;
    jwtService = new JwtService(jwtSecret, tokenTtlMinutes);
  }

  @Test
  void givenRequest_whenGenerateToken_thenReturnedCorrectToken() {

    JoinGameRequest request = new JoinGameRequest("gameId_test", "username_test");
    String token = jwtService.generateToken(request);

    Jws<Claims> jwt = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(token);

    assertThat(jwt.getBody().getSubject()).isEqualTo("username_test");
    assertThat(jwt.getBody().get("gameId")).isEqualTo("gameId_test");
    assertThat(jwt.getBody().getIssuedAt()).isBefore(Date.from(Instant.now()));
    assertThat(jwt.getBody().getExpiration()).isBefore(Date.from(Instant.now().plus(tokenTtlMinutes, ChronoUnit.MINUTES)));
  }

  @Test
  void givenToken_whenGetPlayer_returnedCorrectPlayer() {

    String token = Jwts.builder()
        .setSubject("AlexanderPushkin")
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(5))))
        .claim("gameId", "GreatBattle")
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
        .compact();

    GamePlayer player = jwtService.getPlayer(token);
    assertThat(player).isEqualTo(new GamePlayer("GreatBattle", "AlexanderPushkin"));
  }
}