package nl.robinthedev.catanjr.game.model.board.builder;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import nl.robinthedev.catanjr.game.model.board.Board;
import nl.robinthedev.catanjr.game.model.board.FortSite;
import nl.robinthedev.catanjr.game.model.board.LandTile;
import nl.robinthedev.catanjr.game.model.board.Occupant;
import nl.robinthedev.catanjr.game.model.board.RequiredDiceRoll;
import nl.robinthedev.catanjr.game.model.board.ResourceType;

class BoardBuilder {

  private Map<String, LandTileBuilder> landTiles;
  private Map<Integer, FortSiteBuilder> fortSites;

  BoardBuilder() {
    this.landTiles = HashMap.empty();
    this.fortSites = HashMap.empty();
  }

  public void addLandTile(String id, ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    landTiles = landTiles.put(id, LandTileBuilder.of(resourceType, requiredDiceRoll));
  }

  public void surround(String sheep5, int... fortSites) {
    surround(sheep5, false, fortSites);
  }

  public void surroundCircular(String sheep5, int... fortSites) {
    surround(sheep5, true, fortSites);
  }

  private void surround(String landTile, boolean circular, int[] fortSitesToConnect) {
    FortSiteBuilder previous = null;
    for (int i = 0; i < fortSitesToConnect.length; i++) {
      var current = getFortById(fortSitesToConnect[i]);
      current.connectTo(landTiles.get(landTile).get());
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

  public void setOccupant(Occupant occupant, int fortSite, int betweenFort) {
    getFortById(fortSite).setOccupant(occupant, getFortById(betweenFort));
  }

  private FortSiteBuilder getFortById(int id) {
    var change = fortSites.computeIfAbsent(id, key -> new FortSiteBuilder(id));
    fortSites = change._2();
    return change._1();
  }

  public Board build() {
    return new Board(toLandTiles(landTiles), toFortSites(fortSites));
  }

  private static Map<String, LandTile> toLandTiles(Map<String, LandTileBuilder> landTiles) {
    return landTiles.mapValues(LandTileBuilder::build);
  }

  private static Map<Integer, FortSite> toFortSites(Map<Integer, FortSiteBuilder> fortSites) {
    return fortSites.mapValues(FortSiteBuilder::build);
  }
}
