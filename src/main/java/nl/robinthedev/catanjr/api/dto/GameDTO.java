package nl.robinthedev.catanjr.api.dto;

import java.util.List;
import nl.robinthedev.catanjr.game.model.Game;

public record GameDTO(
    PlayerDTO firstPlayer,
    PlayerDTO secondPlayer,
    InventoryDTO bankInventory,
    List<BuoyDTO> buoyInventory,
    List<ShipYardDTO> shipYards) {
  public static GameDTO of(Game game) {
    var firstPlayer = PlayerDTO.of(game.firstPlayer());
    var secondPlayer = PlayerDTO.of(game.secondPlayer());
    var bankInventory = InventoryDTO.of(game.bankInventory());
    var buoyInventory = BuoyDTO.of(game.buoyInventory());
    return new GameDTO(
        firstPlayer,
        secondPlayer,
        bankInventory,
        buoyInventory,
        ShipYardDTO.from2PlayerGame(game.board()));
  }

  public GameDTO setFirstPlayerInventory(InventoryDTO newInventory) {
    return new GameDTO(
        firstPlayer.updateInventory(newInventory),
        secondPlayer,
        bankInventory,
        buoyInventory,
        shipYards);
  }

  public GameDTO setSecondPlayerInventory(InventoryDTO newInventory) {
    return new GameDTO(
        firstPlayer,
        secondPlayer.updateInventory(newInventory),
        bankInventory,
        buoyInventory,
        shipYards);
  }

  public GameDTO setBankInventory(InventoryDTO newInventory) {
    return new GameDTO(firstPlayer, secondPlayer, newInventory, buoyInventory, shipYards);
  }
}
