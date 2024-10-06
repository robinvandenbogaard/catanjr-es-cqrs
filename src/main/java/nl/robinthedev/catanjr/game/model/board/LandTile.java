package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.resources.GainedResources;

class LandTile {
  private final ResourceType resourceType;
  private final RequiredDiceRoll requiredDiceRoll;
  private Occupant occupant;

  private LandTile(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    this.resourceType = resourceType;
    this.requiredDiceRoll = requiredDiceRoll;
    this.occupant = Occupant.EMPTY;
  }

  public static LandTile of(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    return new LandTile(resourceType, requiredDiceRoll);
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

  public boolean gainsOnRoll(DiceRoll diceRoll) {
    return requiredDiceRoll.gainsOn(diceRoll);
  }

  public GainedResources oneResource() {
    return switch (resourceType) {
      case SWORD -> GainedResources.swords(1);
      case GOLD -> GainedResources.gold(1);
      case PINEAPPLE -> GainedResources.pineApple(1);
      case SHEEP -> GainedResources.sheep(1);
      case WOOD -> GainedResources.wood(1);
    };
  }
}
