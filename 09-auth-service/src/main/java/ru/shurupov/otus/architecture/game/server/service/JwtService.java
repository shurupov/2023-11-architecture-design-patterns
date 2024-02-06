package ru.shurupov.otus.architecture.game.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import ru.shurupov.otus.architecture.game.server.controller.request.AuthRequest;

public class JwtService {

  public final static String JWT_SECRET = "gameserverjwttokensecretgameserverjwttokensecretgameserverjwttokensecretgameserverjwttokensecret";
  public final static long TOKEN_TIMEOUT_MINUTES = 120;

  private final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

  public String generateToken(String username) {

    String token = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(Date.from(getIssuedTime()))
        .setExpiration(Date.from(getExpireTime()))
        .signWith(key)
        .compact();

    return token;
  }

  public String getUsername(String token) {
    Jws<Claims> jwt = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
    return jwt.getBody().getSubject();
  }

  private Instant getExpireTime() {
    return LocalDateTime.now().plusMinutes(TOKEN_TIMEOUT_MINUTES).atZone(ZoneId.systemDefault()).toInstant();
  }

  private Instant getIssuedTime() {
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
  }

}
