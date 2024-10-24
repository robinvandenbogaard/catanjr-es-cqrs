package nl.robinthedev.catanjr.api.dto;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import java.util.Objects;
import nl.robinthedev.catanjr.game.model.Game;

public record GameDTO(
    String title,
    PlayerDTO firstPlayer,
    PlayerDTO secondPlayer,
    InventoryDTO bankInventory,
    List<BuoyDTO> buoyInventory,
    List<FortSiteDTO> fortSites,
    Set<ShipYardDTO> shipYards) {
  public static GameDTO of(Game game) {
    var firstPlayer = PlayerDTO.of(game.firstPlayer());
    var secondPlayer = PlayerDTO.of(game.secondPlayer());
    var bankInventory = InventoryDTO.of(game.bankInventory());
    var buoyInventory = BuoyDTO.of(game.buoyInventory());
    return new GameDTO(
        game.title().value(),
        firstPlayer,
        secondPlayer,
        bankInventory,
        buoyInventory,
        FortSiteDTO.from2PlayerGame(game.board()),
        ShipYardDTO.from2PlayerGame(game.board()));
  }

  public GameDTO setFirstPlayerInventory(InventoryDTO newInventory) {
    return new GameDTO(
        title,
        firstPlayer.updateInventory(newInventory),
        secondPlayer,
        bankInventory,
        buoyInventory,
        fortSites,
        shipYards);
  }

  public GameDTO setSecondPlayerInventory(InventoryDTO newInventory) {
    return new GameDTO(
        title,
        firstPlayer,
        secondPlayer.updateInventory(newInventory),
        bankInventory,
        buoyInventory,
        fortSites,
        shipYards);
  }

  public GameDTO setFirstPlayerActions(Set<ActionDTO> newActions) {
    return new GameDTO(
        title,
        firstPlayer.updateActions(newActions),
        secondPlayer,
        bankInventory,
        buoyInventory,
        fortSites,
        shipYards);
  }

  public GameDTO setSecondPlayerActions(Set<ActionDTO> newActions) {
    return new GameDTO(
        title,
        firstPlayer,
        secondPlayer.updateActions(newActions),
        bankInventory,
        buoyInventory,
        fortSites,
        shipYards);
  }

  public GameDTO setBankInventory(InventoryDTO newInventory) {
    return new GameDTO(
        title, firstPlayer, secondPlayer, newInventory, buoyInventory, fortSites, shipYards);
  }

  public GameDTO updateFort(FortSiteDTO fortSite) {
    var newFortSites = fortSites.removeFirst(site -> site.id() == fortSite.id()).append(fortSite);
    return new GameDTO(
        title, firstPlayer, secondPlayer, bankInventory, buoyInventory, newFortSites, shipYards);
  }

  public GameDTO updateShip(ShipYardDTO shipYard) {
    var oldShip = shipYards.find(ship -> Objects.equals(ship.id(), shipYard.id())).get();
    var newShipYards = shipYards.remove(oldShip).add(shipYard);
    return new GameDTO(
        title, firstPlayer, secondPlayer, bankInventory, buoyInventory, fortSites, newShipYards);
  }
}
