package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
import nl.robinthedev.catanjr.api.event.BuyShip;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.ShipBought;
import nl.robinthedev.catanjr.game.model.board.ShiteSiteOccupiedException;
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

  @Test
  void can_buy_ship() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(1, 1, 1, 1, 1)))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, 3))
        .expectEvents(
            new ShipBought(GAME_ID, ACCOUNT_PLAYER_1, new ShipYardDTO("3", OwnerDTO.PLAYER1)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void can_not_buy_ship_on_spot_that_is_already_taken() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(1, 1, 1, 1, 1)))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, 2))
        .expectException(ShiteSiteOccupiedException.class);
  }
}
