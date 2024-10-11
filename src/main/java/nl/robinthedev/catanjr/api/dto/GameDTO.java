package nl.robinthedev.catanjr.api.dto;

import java.util.List;
import java.util.Set;
import nl.robinthedev.catanjr.game.model.Game;

public record GameDTO(
    PlayerDTO firstPlayer,
    PlayerDTO secondPlayer,
    InventoryDTO bankInventory,
    List<BuoyDTO> buoyInventory,
    List<FortSiteDTO> fortSites) {
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
        FortSiteDTO.from2PlayerGame(game.board()));
  }

  public GameDTO setFirstPlayerInventory(InventoryDTO newInventory) {
    return new GameDTO(
        firstPlayer.updateInventory(newInventory),
        secondPlayer,
        bankInventory,
        buoyInventory,
        fortSites);
  }

  public GameDTO setSecondPlayerInventory(InventoryDTO newInventory) {
    return new GameDTO(
        firstPlayer,
        secondPlayer.updateInventory(newInventory),
        bankInventory,
        buoyInventory,
        fortSites);
  }

  public GameDTO setFirstPlayerActions(Set<ActionDTO> newActions) {
    return new GameDTO(
        firstPlayer.updateActions(newActions),
        secondPlayer,
        bankInventory,
        buoyInventory,
        fortSites);
  }

  public GameDTO setSecondPlayerActions(Set<ActionDTO> newActions) {
    return new GameDTO(
        firstPlayer,
        secondPlayer.updateActions(newActions),
        bankInventory,
        buoyInventory,
        fortSites);
  }

  public GameDTO setBankInventory(InventoryDTO newInventory) {
    return new GameDTO(firstPlayer, secondPlayer, newInventory, buoyInventory, fortSites);
  }
}
