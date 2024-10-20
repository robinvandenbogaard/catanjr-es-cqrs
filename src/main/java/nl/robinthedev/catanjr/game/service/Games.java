package nl.robinthedev.catanjr.game.service;

import io.vavr.collection.Set;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.ActionDTO;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;

public interface Games {
  void save(GameId gameId, GameDTO game);

  GameDTO get(GameId gameId);

  void updatePlayerInventory(GameId gameId, UUID playerAccount, InventoryDTO newInventory);

  void updateBankInventory(GameId gameId, InventoryDTO newInventory);

  void updatePlayerActions(GameId gameId, UUID playerAccount, Set<ActionDTO> newActions);

  void updateBoard(GameId gameId, FortSiteDTO fortSite);

  void updateBoard(GameId gameId, ShipYardDTO shipYard);
}
