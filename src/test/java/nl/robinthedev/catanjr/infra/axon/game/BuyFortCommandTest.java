package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.command.BuyFort;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.FortBought;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.game.model.board.FortSiteOccupiedException;
import nl.robinthedev.catanjr.game.model.round.ActionNotAllowedException;
import nl.robinthedev.catanjr.game.model.round.NotYourTurnException;
import org.junit.jupiter.api.Test;

public class BuyFortCommandTest extends AbstractGameAggregateTest {

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
  void can_buy_fort() {
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1))
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 0, 0, 0),
                new InventoryDTO(1, 1, 1, 1, 1)))
        .when(new BuyFort(GAME_ID, ACCOUNT_PLAYER_1, 3))
        .expectEvents(
            new FortBought(GAME_ID, ACCOUNT_PLAYER_1, new FortSiteDTO(3, OwnerDTO.PLAYER1)))
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
  void can_not_buy_fort_twice() {
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
}
