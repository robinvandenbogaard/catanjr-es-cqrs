package nl.robinthedev.catanjr.api.dto;

import nl.robinthedev.catanjr.game.model.Game;

public record GameDTO(
    PlayerDTO firstPlayer,
    PlayerDTO secondPlayer,
    InventoryDTO bankInventory,
    InventoryDTO buoyInventory) {
  public static GameDTO of(Game game) {
    var firstPlayer = PlayerDTO.of(game.firstPlayer());
    var secondPlayer = PlayerDTO.of(game.secondPlayer());
    var bankInventory = InventoryDTO.of(game.bankInventory());
    var buoyInventory = InventoryDTO.of(game.buoyInventory());
    return new GameDTO(firstPlayer, secondPlayer, bankInventory, buoyInventory);
  }
}
