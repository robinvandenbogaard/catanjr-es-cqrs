package nl.robinthedev.catanjr.game.model.board.builder;

import nl.robinthedev.catanjr.game.model.board.LandTile;
import nl.robinthedev.catanjr.game.model.board.Occupant;
import nl.robinthedev.catanjr.game.model.board.RequiredDiceRoll;
import nl.robinthedev.catanjr.game.model.board.ResourceType;

class LandTileBuilder {
  private final ResourceType resourceType;
  private final RequiredDiceRoll requiredDiceRoll;
  private final Occupant occupant;

  private LandTileBuilder(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    this.resourceType = resourceType;
    this.requiredDiceRoll = requiredDiceRoll;
    this.occupant = Occupant.EMPTY;
  }

  public static LandTileBuilder of(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    return new LandTileBuilder(resourceType, requiredDiceRoll);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LandTileBuilder landTile = (LandTileBuilder) o;
    return resourceType == landTile.resourceType
        && requiredDiceRoll == landTile.requiredDiceRoll
        && occupant == landTile.occupant;
  }

  public LandTile build() {
    return new LandTile(resourceType, requiredDiceRoll, occupant);
  }

  @Override
  public int hashCode() {
    int result = resourceType.hashCode();
    result = 31 * result + requiredDiceRoll.hashCode();
    result = 31 * result + occupant.hashCode();
    return result;
  }
}
