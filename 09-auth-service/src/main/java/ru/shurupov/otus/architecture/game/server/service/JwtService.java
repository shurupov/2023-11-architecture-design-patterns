package ru.shurupov.otus.architecture.game.server.service;

import static spark.Spark.halt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;

public class JwtService {

  private final String jwtSecret;
  private final long tokenTtlMinutes;

  private final Key key;

  public JwtService(String jwtSecret, long tokenTtlMinutes) {
    this.jwtSecret = jwtSecret;
    this.tokenTtlMinutes = tokenTtlMinutes;
    key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(JoinGameRequest joinGameRequest) {

    String token = Jwts.builder()
        .setSubject(joinGameRequest.getUsername())
        .setIssuedAt(Date.from(getIssuedTime()))
        .setExpiration(Date.from(getExpireTime()))
        .claim("gameId", joinGameRequest.getGameId())
        .signWith(key)
        .compact();

    return token;
  }

  public GamePlayer getPlayer(String token) {
    try {
      Jws<Claims> jwt = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return new GamePlayer(jwt.getBody().get("gameId", String.class), jwt.getBody().getSubject());
    } catch (JwtException e) {
      halt(401, "Your token is invalid");
      return null;
    }
  }

  private Instant getExpireTime() {
    return LocalDateTime.now().plusMinutes(tokenTtlMinutes).atZone(ZoneId.systemDefault()).toInstant();
  }

  private Instant getIssuedTime() {
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
  }

}
