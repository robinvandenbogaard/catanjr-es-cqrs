package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.event.BuyShip;
import nl.robinthedev.catanjr.game.model.round.ActionNotAllowedException;
import nl.robinthedev.catanjr.game.model.round.NotYourTurnException;
import org.junit.jupiter.api.Test;

public class BuyShipCommandTest extends AbstractGameAggregateTest {
  
  @Test
  void cannot_buy_if_its_not_your_turn() {
    fixture
        .given(getGameCreatedEvent())
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_2, 3))
        .expectException(NotYourTurnException.class);
  }

  @Test
  void cannot_buy_before_rolling_the_dice() {
    fixture
        .given(getGameCreatedEvent())
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, 3))
        .expectException(ActionNotAllowedException.class);
  }
}
