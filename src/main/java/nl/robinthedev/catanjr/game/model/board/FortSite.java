package nl.robinthedev.catanjr.game.model.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

class FortSite {
  private final int id;
  private final List<ShipSite> neighbours;
  private final Set<LandTile> landTiles;
  private Occupant occupant;

  public FortSite(int id) {
    this.id = id;
    this.neighbours = new ArrayList<>();
    this.landTiles = new HashSet<>();
    this.occupant = Occupant.EMPTY;
  }

  public void connectTo(LandTile landTile) {
    landTiles.add(landTile);
  }

  public void connectTo(FortSite other) {
    if (neighbours.stream().anyMatch(shipSite -> shipSite.isConnectedTo(other))) return;

    var bridge = new ShipSite(this, other);
    neighbours.add(bridge);
    other.neighbours.add(bridge);
  }

  public void setOccupant(Occupant occupant, FortSite betweenFort) {
    this.occupant = occupant;
    var shipSite =
        neighbours.stream()
            .filter(site -> site.isConnectedTo(betweenFort))
            .findFirst()
            .orElseThrow();
    shipSite.setOccupant(occupant);
  }

  public Occupant getOccupant() {
    return occupant;
  }

  public boolean belongsTo(Occupant occupant) {
    return this.occupant == occupant;
  }

  public ResourceChanges getResources(DiceRoll diceRoll) {
    return landTiles.stream()
        .filter(tile -> tile.gainsOnRoll(diceRoll))
        .map(LandTile::oneResource)
        .findFirst()
        .orElse(ResourceChanges.EMPTY);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FortSite fortSite = (FortSite) o;
    return id == fortSite.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
