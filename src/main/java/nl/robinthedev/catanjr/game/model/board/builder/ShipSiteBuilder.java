package nl.robinthedev.catanjr.game.model.board.builder;

import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.board.Occupant;
import nl.robinthedev.catanjr.game.model.board.ShipSite;

class ShipSiteBuilder {

  private final FortSiteBuilder from;
  private final FortSiteBuilder to;
  private Occupant occupant;

  public ShipSiteBuilder(FortSiteBuilder from, FortSiteBuilder to) {
    this.from = from;
    this.to = to;
    this.occupant = Occupant.EMPTY;
  }

  public boolean isConnectedTo(FortSiteBuilder other) {
    return other.equals(from) || other.equals(to);
  }

  public void setOccupant(Occupant occupant) {
    this.occupant = occupant;
  }

  ShipSite build() {
    return new ShipSite(new SiteId(from.id), new SiteId(to.id), occupant);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ShipSiteBuilder shipSite = (ShipSiteBuilder) o;
    return from.equals(shipSite.from) && to.equals(shipSite.to) && occupant == shipSite.occupant;
  }

  @Override
  public int hashCode() {
    int result = from.hashCode();
    result = 31 * result + to.hashCode();
    result = 31 * result + occupant.hashCode();
    return result;
  }
}
