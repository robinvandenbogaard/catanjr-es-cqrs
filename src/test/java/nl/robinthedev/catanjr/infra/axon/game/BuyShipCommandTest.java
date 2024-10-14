package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.command.BuyShip;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.ShipBought;
import nl.robinthedev.catanjr.game.model.board.ShipYardOccupiedException;
import nl.robinthedev.catanjr.game.model.round.ActionNotAllowedException;
import nl.robinthedev.catanjr.game.model.round.NotYourTurnException;
import org.junit.jupiter.api.Test;

class BuyShipCommandTest extends AbstractGameAggregateTest {

  @Test
  void cannot_buy_if_its_not_your_turn() {
    fixture
        .given(getGameCreatedEvent())
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_2, "2-3"))
        .expectException(NotYourTurnException.class);
  }

  @Test
  void cannot_buy_before_rolling_the_dice() {
    fixture
        .given(getGameCreatedEvent())
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, "1-3"))
        .expectException(ActionNotAllowedException.class);
  }

  @Test
  void can_buy_ship() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, "1-3"))
        .expectEvents(
            new ShipBought(GAME_ID, ACCOUNT_PLAYER_1, new ShipYardDTO("1-3", OwnerDTO.PLAYER1)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void can_not_buy_ship_on_spot_that_is_already_taken() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, "4-6"))
        .expectException(ShipYardOccupiedException.class);
  }

  @Test
  void can_not_buy_same_ship_twice() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new ShipBought(GAME_ID, ACCOUNT_PLAYER_1, new ShipYardDTO("1-3", OwnerDTO.PLAYER1)))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, "1-3"))
        .expectException(ShipYardOccupiedException.class);
  }
}
