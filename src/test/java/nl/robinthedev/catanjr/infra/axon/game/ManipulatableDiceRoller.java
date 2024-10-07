package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.service.DiceRoller;

class ManipulatableDiceRoller implements DiceRoller {
  private int desiredDiceRoll;

  public void nextRollIs(int desiredDiceRoll) {
    this.desiredDiceRoll = desiredDiceRoll;
  }

  @Override
  public DiceRoll roll() {
    return DiceRoll.valueOf(desiredDiceRoll);
  }
}
