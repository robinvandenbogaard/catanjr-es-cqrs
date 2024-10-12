package nl.robinthedev.catanjr.game.model.board;

import io.vavr.collection.Map;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public record Board(Map<String, LandTile> landTiles, Map<Integer, FortSite> fortSites) {

  private FortSite getFortById(int id) {
    return fortSites
        .get(id)
        .getOrElseThrow(() -> new IllegalStateException("id " + id + " not found"));
  }

  public BoardPlayer getFortOwner(SiteId fortSiteId) {
    return switch (getFortById(fortSiteId.value()).occupant()) {
      case EMPTY -> BoardPlayer.NONE;
      case PLAYER1 -> BoardPlayer.PLAYER1;
      case PLAYER2 -> BoardPlayer.PLAYER2;
      case PLAYER3 -> BoardPlayer.PLAYER3;
      case PLAYER4 -> BoardPlayer.PLAYER4;
      case CAPTAIN -> throw new IllegalStateException("captain can't own forts");
    };
  }

  public ResourceChanges getResources(DiceRoll diceRoll, BoardPlayer player) {
    var occupant = Occupant.of(player);
    return fortSites
        .values()
        .filter(site -> site.belongsTo(occupant))
        .map(site -> site.getResources(diceRoll))
        .reduce(ResourceChanges::add);
  }

  public Board markFortOwned(SiteId siteId, BoardPlayer nr) {
    var fort = getFortById(siteId.value());
    return new Board(
        landTiles, fortSites.put(siteId.value(), fort.updateOccupant(Occupant.of(nr))));
  }

  public void mustBeUnoccupied(SiteId siteId) {
    if (!getFortById(siteId.value()).occupant().equals(Occupant.EMPTY)) {
      throw new FortSiteOccupiedException("This fort site is already occupied");
    }
  }

  public void mustHaveAdjecentShip(SiteId siteId, BoardPlayer nr) {
    Occupant occupant = Occupant.of(nr);
    if (getFortById(siteId.value()).withShipsOwnedBy(occupant).isEmpty()) {
      throw new FortSiteIsMissingAdjecentShipException("No ships belong to " + occupant);
    }
  }
}
