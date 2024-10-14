package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.game.model.ShipId;
import nl.robinthedev.catanjr.game.model.SiteId;

public record ShipSite(SiteId from, SiteId to, Occupant occupant) {

  public boolean belongsTo(Occupant occupant) {
    return this.occupant.equals(occupant);
  }

  public ShipId getShipId() {
    return new ShipId(
        Math.min(from.value(), to.value()) + "-" + Math.max(from.value(), to.value()));
  }

  public BoardPlayer getOwner() {
    return switch (occupant) {
      case EMPTY -> BoardPlayer.NONE;
      case CAPTAIN -> throw new IllegalStateException("Ships cannot be owned by Captain!");
      case PLAYER1 -> BoardPlayer.PLAYER1;
      case PLAYER2 -> BoardPlayer.PLAYER2;
      case PLAYER3 -> BoardPlayer.PLAYER3;
      case PLAYER4 -> BoardPlayer.PLAYER4;
    };
  }

  public boolean hasId(ShipId shipSite) {
    return getShipId().equals(shipSite);
  }

  public ShipSite updateOccupant(Occupant occupant) {
    return new ShipSite(from, to, occupant);
  }
}
