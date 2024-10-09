package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

class LandTile {
  private final ResourceType resourceType;
  private final RequiredDiceRoll requiredDiceRoll;
  private final Occupant occupant;

  private LandTile(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    this.resourceType = resourceType;
    this.requiredDiceRoll = requiredDiceRoll;
    this.occupant = Occupant.EMPTY;
  }

  public static LandTile of(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    return new LandTile(resourceType, requiredDiceRoll);
  }

  public boolean gainsOnRoll(DiceRoll diceRoll) {
    return requiredDiceRoll.gainsOn(diceRoll);
  }

  public ResourceChanges oneResource() {
    return switch (resourceType) {
      case SWORD -> ResourceChanges.swords(1);
      case GOLD -> ResourceChanges.gold(1);
      case PINEAPPLE -> ResourceChanges.pineApple(1);
      case SHEEP -> ResourceChanges.sheep(1);
      case WOOD -> ResourceChanges.wood(1);
    };
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LandTile landTile = (LandTile) o;
    return resourceType == landTile.resourceType
        && requiredDiceRoll == landTile.requiredDiceRoll
        && occupant == landTile.occupant;
  }

  @Override
  public int hashCode() {
    int result = resourceType.hashCode();
    result = 31 * result + requiredDiceRoll.hashCode();
    result = 31 * result + occupant.hashCode();
    return result;
  }
}
