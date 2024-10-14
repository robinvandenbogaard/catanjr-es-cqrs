package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.command.BuyShip;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.FortBought;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.ShipBought;
import nl.robinthedev.catanjr.game.model.board.ShipYardIsMissingAdjecentFortException;
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
        .andGiven(new FortBought(GAME_ID, ACCOUNT_PLAYER_1, new FortSiteDTO(3, OwnerDTO.PLAYER1)))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(0, 0, 1, 1, 0)))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, "1-3"))
        .expectEvents(
            new ShipBought(GAME_ID, ACCOUNT_PLAYER_1, new ShipYardDTO("1-3", OwnerDTO.PLAYER1)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 1, 1, 0),
                new InventoryDTO(0, 0, 0, 0, 0)),
            new BankInventoryChanged(
                GAME_ID,
                new InventoryDTO(17, 17, 15, 17, 15),
                new InventoryDTO(17, 17, 16, 18, 15)))
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

  @Test
  void can_not_buy_ship_that_is_not_connected_by_forts() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, "7-8"))
        .expectException(ShipYardIsMissingAdjecentFortException.class);
  }

  @Test
  void can_not_buy_ship_if_player_does_not_have_enough_resources() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(new FortBought(GAME_ID, ACCOUNT_PLAYER_1, new FortSiteDTO(3, OwnerDTO.PLAYER1)))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(0, 0, 0, 0, 0)))
        .when(new BuyShip(GAME_ID, ACCOUNT_PLAYER_1, "1-3"))
        .expectException(NotEnoughResources.class);
  }
}
