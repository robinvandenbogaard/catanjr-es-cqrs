package nl.robinthedev.catanjr.infra.persistence;

import io.vavr.collection.Set;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import nl.robinthedev.catanjr.api.dto.ActionDTO;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
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

  @Override
  public void updatePlayerActions(GameId gameId, UUID playerAccount, Set<ActionDTO> newActions) {
    var game = games.get(gameId);
    if (game.firstPlayer().accountId().equals(playerAccount)) {
      game = game.setFirstPlayerActions(newActions);
    } else if (game.secondPlayer().accountId().equals(playerAccount)) {
      game = game.setSecondPlayerActions(newActions);
    }
    games.put(gameId, game);
    log.info("Player actions updated: {} to {}", playerAccount, newActions);
  }

  @Override
  public void updateBoard(GameId gameId, FortSiteDTO fortSite) {
    var game = games.get(gameId);
    game = game.updateFort(fortSite);
    games.put(gameId, game);
    log.info("Board updated: {}", fortSite);
  }

  @Override
  public void updateBoard(GameId gameId, ShipYardDTO shipYard) {
    var game = games.get(gameId);
    game = game.updateShip(shipYard);
    games.put(gameId, game);
    log.info("Board updated: {}", shipYard);
  }
}
