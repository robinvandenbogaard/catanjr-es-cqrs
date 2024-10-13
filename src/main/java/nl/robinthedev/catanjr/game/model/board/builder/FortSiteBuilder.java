package nl.robinthedev.catanjr.game.model.board.builder;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.board.FortSite;
import nl.robinthedev.catanjr.game.model.board.Occupant;

class FortSiteBuilder {
  final int id;
  private List<ShipSiteBuilder> neighbours;
  private Set<LandTileBuilder> landTiles;
  private Occupant occupant;

  public FortSiteBuilder(int id) {
    this.id = id;
    this.neighbours = List.empty();
    this.landTiles = HashSet.empty();
    this.occupant = Occupant.EMPTY;
  }

  public void connectTo(LandTileBuilder landTile) {
    landTiles = landTiles.add(landTile);
  }

  public void connectTo(FortSiteBuilder other) {
    if (neighbours.find(shipSite -> shipSite.isConnectedTo(other)).isDefined()) return;

    var bridge = new ShipSiteBuilder(this, other);
    neighbours = neighbours.append(bridge);
    other.add(bridge);
  }

  private void add(ShipSiteBuilder bridge) {
    neighbours = neighbours.append(bridge);
  }

  public void setOccupant(Occupant occupant, FortSiteBuilder betweenFort) {
    this.occupant = occupant;
    var shipSite = neighbours.filter(site -> site.isConnectedTo(betweenFort)).get();
    shipSite.setOccupant(occupant);
  }

  FortSite build() {
    return new FortSite(
        new SiteId(id),
        neighbours.map(ShipSiteBuilder::build).toSet(),
        landTiles.map(LandTileBuilder::build).toSet(),
        occupant);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FortSiteBuilder fortSiteBuilder = (FortSiteBuilder) o;
    return id == fortSiteBuilder.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
