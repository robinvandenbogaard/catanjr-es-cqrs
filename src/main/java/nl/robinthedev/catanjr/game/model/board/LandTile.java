package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public record LandTile(
    ResourceType resourceType, RequiredDiceRoll requiredDiceRoll, Occupant occupant) {

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
}
