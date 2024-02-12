package ru.shurupov.otus.architecture.game.server.controller.request;

import lombok.Data;

@Data
public class AuthRequest {
  private String username;
  private String password;
}
