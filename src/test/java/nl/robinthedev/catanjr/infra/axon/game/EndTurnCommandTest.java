package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import org.junit.jupiter.api.Test;

class EndTurnCommandTest extends AbstractGameAggregateTest {

  @Test
  void player1_can_not_end_turn_if_dice_needs_to_be_rolled() {
    fixture
        .given(getGameCreatedEvent())
        .when(new EndTurn(GAME_ID, ACCOUNT_PLAYER_1))
        .expectException(IllegalStateException.class);
  }

  @Test
  void player1_can_end_turn_after_dice_roll() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_1))
        .when(new EndTurn(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(new TurnEnded(GAME_ID, ACCOUNT_PLAYER_2));
  }
}
