package nl.robinthedev.catanjr.game.service;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;

public interface Games {
  void save(GameId gameId, GameDTO game);

  GameDTO get(GameId gameId);

  void updatePlayerInventory(GameId gameId, UUID playerAccount, InventoryDTO newInventory);

  void updateBankInventory(GameId gameId, InventoryDTO newInventory);
}
