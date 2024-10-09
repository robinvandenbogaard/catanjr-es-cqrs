package nl.robinthedev.catanjr.game.model.board;

import java.util.HashMap;
import java.util.Map;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

class GraphBoard implements Board {
  private final Map<String, LandTile> landTiles;
  private final Map<Integer, FortSite> fortSites;

  GraphBoard() {
    this.landTiles = new HashMap<>();
    this.fortSites = new HashMap<>();
  }

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

  @Override
  public ResourceChanges getResources(DiceRoll diceRoll, BoardPlayer player) {
    var occupant = Occupant.of(player);
    return fortSites.values().stream()
        .filter(site -> site.belongsTo(occupant))
        .map(site -> site.getResources(diceRoll))
        .reduce(ResourceChanges.EMPTY, ResourceChanges::add);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GraphBoard that = (GraphBoard) o;
    return landTiles.equals(that.landTiles) && fortSites.equals(that.fortSites);
  }

  @Override
  public int hashCode() {
    int result = landTiles.hashCode();
    result = 31 * result + fortSites.hashCode();
    return result;
  }
}
