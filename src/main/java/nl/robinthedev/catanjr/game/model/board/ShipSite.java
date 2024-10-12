package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.game.model.SiteId;

public record ShipSite(SiteId from, SiteId to, Occupant occupant) {
  public boolean belongsTo(Occupant occupant) {
    return this.occupant.equals(occupant);
  }
}
