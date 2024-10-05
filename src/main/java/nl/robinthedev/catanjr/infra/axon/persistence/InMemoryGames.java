package nl.robinthedev.catanjr.infra.axon.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.game.service.Games;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class InMemoryGames implements Games {
  private static final Logger log = LoggerFactory.getLogger(InMemoryGames.class);
  private final Map<GameId, GameDTO> games = new ConcurrentHashMap<>();

  @Override
  public void save(GameId gameId, GameDTO game) {
    games.put(gameId, game);
    log.info("Game saved: {}", gameId);
  }

  @Override
  public GameDTO get(GameId gameId) {
    return games.get(gameId);
  }
}
