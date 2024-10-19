package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.command.BuyFort;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.FortBought;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import nl.robinthedev.catanjr.game.model.board.FortSiteIsMissingAdjecentShipException;
import nl.robinthedev.catanjr.game.model.board.FortSiteOccupiedException;
import nl.robinthedev.catanjr.game.model.player.NotEnoughResources;
import nl.robinthedev.catanjr.game.model.round.ActionNotAllowedException;
import nl.robinthedev.catanjr.game.model.round.NotYourTurnException;
import org.junit.jupiter.api.Test;

class BuyFortCommandTest extends AbstractGameAggregateTest {

  @Test
  void cannot_buy_if_its_not_your_turn() {
    fixture
        .given(getGameCreatedEvent())
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_2, 3))
        .expectException(NotYourTurnException.class);
  }

  @Test
  void cannot_buy_before_rolling_the_dice() {
    fixture
        .given(getGameCreatedEvent())
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_1, 3))
        .expectException(ActionNotAllowedException.class);
  }

  @Test
  void p1_can_buy_fort() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(0, 1, 1, 1, 1)))
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_1, 3))
        .expectEvents(
            new FortBought(GAME_ID, ACCOUNT_PLAYER_1, new FortSiteDTO(3, OwnerDTO.PLAYER1)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 1, 1, 1, 1),
                new InventoryDTO(0, 0, 0, 0, 0)),
            new BankInventoryChanged(
                GAME_ID,
                new InventoryDTO(17, 17, 15, 17, 15),
                new InventoryDTO(17, 18, 16, 18, 16)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void p2_can_buy_fort() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new TurnEnded(GAME_ID, ACCOUNT_PLAYER_2))
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_2))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(0, 1, 1, 1, 1)))
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_2, 6))
        .expectEvents(
            new FortBought(GAME_ID, ACCOUNT_PLAYER_2, new FortSiteDTO(6, OwnerDTO.PLAYER2)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 1, 1, 1, 1),
                new InventoryDTO(0, 0, 0, 0, 0)),
            new BankInventoryChanged(
                GAME_ID,
                new InventoryDTO(17, 17, 15, 17, 15),
                new InventoryDTO(17, 18, 16, 18, 16)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void can_not_buy_fort_on_spot_that_is_already_taken() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(1, 1, 1, 1, 1)))
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_1, 2))
        .expectException(FortSiteOccupiedException.class);
  }

  @Test
  void can_not_buy_same_fort_twice() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(3, 3, 3, 3, 3)))
        .andGiven(new FortBought(GAME_ID, ACCOUNT_PLAYER_1, new FortSiteDTO(3, OwnerDTO.PLAYER1)))
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_1, 3))
        .expectException(FortSiteOccupiedException.class);
  }

  @Test
  void can_not_buy_fort_that_is_not_connected_by_ships() {
    int disconnectedSiteId = 12;
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(3, 3, 3, 3, 3)))
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_1, disconnectedSiteId))
        .expectException(FortSiteIsMissingAdjecentShipException.class);
  }

  @Test
  void can_not_buy_fort_if_player_does_not_have_enough_resources() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(0, 0, 0, 0, 0)))
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_1, 3))
        .expectException(NotEnoughResources.class);
  }
}
