package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.game.model.SiteId;

public record ShipSite(SiteId from, SiteId to, Occupant occupant) {

  public boolean belongsTo(Occupant occupant) {
    return this.occupant.equals(occupant);
  }

  public String getBridgeId() {
    return Math.min(from.value(), to.value()) + "-" + Math.max(from.value(), to.value());
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
}
