package nl.robinthedev.catanjr.infra.dice;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.service.DiceRoller;
import org.springframework.stereotype.Component;

@Component
class RandomDiceRoll implements DiceRoller {
  @Override
  public DiceRoll roll() {
    return DiceRoll.valueOf(1 + (int) (Math.random() * DiceRoll.values().length));
  }
}
