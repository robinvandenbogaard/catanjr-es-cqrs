package nl.robinthedev.catanjr.infra.persistence;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.game.service.PlayerTurn;
import org.springframework.stereotype.Service;

@Service
class InMemoryPlayerTurns implements PlayerTurn {

  private Map<GameId, UUID> turns = HashMap.empty();

  @Override
  public Option<UUID> getCurrentPlayerAccount(GameId gameId) {
    return turns.get(gameId);
  }

  @Override
  public void currentPlayerChangedTo(GameId gameId, UUID uuid) {
    turns = turns.put(gameId, uuid);
  }
}
