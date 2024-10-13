package nl.robinthedev.catanjr.game.model.board;

import io.vavr.collection.Set;
import io.vavr.control.Option;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public record FortSite(
    SiteId id, Set<ShipSite> neighbours, Set<LandTile> landTiles, Occupant occupant) {

  public boolean belongsTo(Occupant occupant) {
    return this.occupant == occupant;
  }

  public ResourceChanges getResources(DiceRoll diceRoll) {
    return landTiles
        .filter(tile -> tile.gainsOnRoll(diceRoll))
        .map(LandTile::oneResource)
        .getOrElse(ResourceChanges.EMPTY);
  }

  public FortSite updateOccupant(Occupant occupant) {
    return new FortSite(id, neighbours, landTiles, occupant);
  }

  public Option<FortSite> withShipsOwnedBy(Occupant occupant) {
    if (!neighbours.exists(ship -> ship.belongsTo(occupant))) {
      return Option.none();
    }
    return Option.of(this);
  }
}
