package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.api.dto.DiceRoll;

public enum RequiredDiceRoll {
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE;

  public boolean gainsOn(DiceRoll diceRoll) {
    return switch (this) {
      case ONE -> diceRoll.equals(DiceRoll.ONE);
      case TWO -> diceRoll.equals(DiceRoll.TWO);
      case THREE -> diceRoll.equals(DiceRoll.THREE);
      case FOUR -> diceRoll.equals(DiceRoll.FOUR);
      case FIVE -> diceRoll.equals(DiceRoll.FIVE);
    };
  }
}
