package nl.robinthedev.catanjr.game.model.board;

import java.util.HashMap;
import java.util.Map;
import nl.robinthedev.catanjr.game.model.SiteId;

class GraphBoard implements Board {
  private final Map<String, LandTile> landTiles = new HashMap<>();
  private final Map<Integer, FortSite> fortSites = new HashMap<>();

  public void addLandTile(String id, ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    landTiles.put(id, LandTile.of(resourceType, requiredDiceRoll));
  }

  public void surround(String sheep5, int... fortSites) {
    surround(sheep5, false, fortSites);
  }

  public void surroundCircular(String sheep5, int... fortSites) {
    surround(sheep5, true, fortSites);
  }

  private void surround(String landTile, boolean circular, int[] fortSitesToConnect) {
    FortSite previous = null;
    for (int i = 0; i < fortSitesToConnect.length; i++) {
      var current = getFortById(fortSitesToConnect[i]);
      current.connectTo(landTiles.get(landTile));
      if (previous != null) {
        previous.connectTo(current);

        if (i == fortSitesToConnect.length - 1 && circular) {
          var first = getFortById(fortSitesToConnect[0]);
          current.connectTo(first);
        }
      }
      previous = current;
    }
  }

  private FortSite getFortById(int id) {
    return fortSites.computeIfAbsent(id, key -> new FortSite(id));
  }

  public void setOccupant(Occupant occupant, int fortSite, int betweenFort) {
    getFortById(fortSite).setOccupant(occupant, getFortById(betweenFort));
  }

  @Override
  public BoardPlayer getOwner(SiteId shipSite) {
    return switch (getFortById(shipSite.value()).getOccupant()) {
      case EMPTY -> BoardPlayer.NONE;
      case PLAYER1 -> BoardPlayer.PLAYER1;
      case PLAYER2 -> BoardPlayer.PLAYER2;
      case PLAYER3 -> BoardPlayer.PLAYER3;
      case PLAYER4 -> BoardPlayer.PLAYER4;
      case CAPTAIN -> throw new IllegalStateException("captain can't own forts");
    };
  }
}
