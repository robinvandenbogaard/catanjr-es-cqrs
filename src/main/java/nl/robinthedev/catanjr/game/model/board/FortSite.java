package nl.robinthedev.catanjr.game.model.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class FortSite {
  private final int id;
  private final List<ShipSite> neighbours = new ArrayList<>();
  private final Set<LandTile> landTiles = new HashSet<>();
  private Occupant occupant = Occupant.EMPTY;

  public FortSite(int id) {
    this.id = id;
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
}
