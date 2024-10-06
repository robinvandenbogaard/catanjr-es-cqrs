package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.api.dto.DiceRoll;

enum RequiredDiceRoll {
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE;

  public boolean gainsOn(DiceRoll diceRoll) {
    return switch (this) {
      case ONE -> diceRoll.equals(DiceRoll.ONE);
      case TWO -> false;
      case THREE -> false;
      case FOUR -> false;
      case FIVE -> false;
    };
  }
}
