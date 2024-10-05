package nl.robinthedev.catanjr.game.model.board;

class ShipSite {

  private final FortSite from;
  private final FortSite to;
  private Occupant occupant = Occupant.EMPTY;

  public ShipSite(FortSite from, FortSite to) {
    this.from = from;
    this.to = to;
  }

  public boolean isConnectedTo(FortSite other) {
    return other.equals(from) || other.equals(to);
  }

  public void setOccupant(Occupant occupant) {
    this.occupant = occupant;
  }
}
