package nl.robinthedev.catanjr.game.model.board;

class ShipSite {

  private final FortSite from;
  private final FortSite to;
  private Occupant occupant;

  public ShipSite(FortSite from, FortSite to) {
    this.from = from;
    this.to = to;
    this.occupant = Occupant.EMPTY;
  }

  public boolean isConnectedTo(FortSite other) {
    return other.equals(from) || other.equals(to);
  }

  public void setOccupant(Occupant occupant) {
    this.occupant = occupant;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ShipSite shipSite = (ShipSite) o;
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
