package nl.robinthedev.catanjr.infra.axon.persistence;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
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

  @Override
  public void updatePlayerInventory(GameId gameId, UUID playerAccount, InventoryDTO newInventory) {
    var game = games.get(gameId);
    if (game.firstPlayer().accountId().equals(playerAccount)) {
      game = game.setFirstPlayerInventory(newInventory);
    } else if (game.secondPlayer().accountId().equals(playerAccount)) {
      game = game.setSecondPlayerInventory(newInventory);
    }
    games.put(gameId, game);
    log.info("Player inventory updated: {} to {}", playerAccount, newInventory);
  }

  @Override
  public void updateBankInventory(GameId gameId, InventoryDTO newInventory) {
    var game = games.get(gameId);
    game = game.setBankInventory(newInventory);
    games.put(gameId, game);
    log.info("Bank inventory updated {}", newInventory);
  }
}
