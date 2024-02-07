package ru.shurupov.otus.architecture.game.server.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;

/*
* This is test implementation.
* But in another implementation interaction will be with IoC with game scopes.
* */
@Slf4j
public class GameServiceMapSetImpl implements GameService {

  private final Map<String, Set<String>> games = new ConcurrentHashMap<>();

  @Override
  public String addGame(Collection<String> participants) {
    String gameId;
    do {
      gameId = RandomStringUtils.random(20, true, true);
    } while (games.containsKey(gameId));
    games.put(gameId, Set.copyOf(participants));
    return gameId;
  }

  @Override
  public boolean containsGame(String gameId) {
    return games.containsKey(gameId);
  }

  @Override
  public boolean containsParticipant(String gameId, String participant) {
    return games.get(gameId).contains(participant);
  }

  @Override
  public void passCommand(GamePlayer player, GameCommandMessage message) {
    log.info("CommandAdded: User: {}, Game: {}, Command: {}", player.getUsername(), player.getGameId(), message);
  }
}
