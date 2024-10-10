package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import org.junit.jupiter.api.Test;

public class RollDiceCommandTest extends AbstractGameAggregateTest {

  @Test
  void first_player_rolls_1() {
    whereNextDiceRollIs(1);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(
            new DiceRolled(GAME_ID, DiceRoll.ONE, ACCOUNT_PLAYER_1),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 0, 1, 0, 2)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 0, 1, 1, 1)),
            new BankInventoryChanged(
                GAME_ID,
                new InventoryDTO(17, 17, 15, 17, 15),
                new InventoryDTO(17, 17, 15, 16, 14)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void first_player_rolls_2() {
    whereNextDiceRollIs(2);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(
            new DiceRolled(GAME_ID, DiceRoll.TWO, ACCOUNT_PLAYER_1),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 0, 1, 1, 1)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 0, 1, 0, 2)),
            new BankInventoryChanged(
                GAME_ID,
                new InventoryDTO(17, 17, 15, 17, 15),
                new InventoryDTO(17, 17, 15, 16, 14)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void first_player_rolls_3() {
    whereNextDiceRollIs(3);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(
            new DiceRolled(GAME_ID, DiceRoll.THREE, ACCOUNT_PLAYER_1),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 0, 2, 0, 1)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 1, 1, 0, 1)),
            new BankInventoryChanged(
                GAME_ID,
                new InventoryDTO(17, 17, 15, 17, 15),
                new InventoryDTO(17, 16, 14, 17, 15)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void first_player_rolls_4() {
    whereNextDiceRollIs(4);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(
            new DiceRolled(GAME_ID, DiceRoll.FOUR, ACCOUNT_PLAYER_1),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 1, 1, 0, 1)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 0, 2, 0, 1)),
            new BankInventoryChanged(
                GAME_ID,
                new InventoryDTO(17, 17, 15, 17, 15),
                new InventoryDTO(17, 16, 14, 17, 15)))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void first_player_rolls_5() {
    whereNextDiceRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_1))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void cannot_roll_multiple_times_for_the_same_player() {
    whereNextDiceRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_1))
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectException(IllegalStateException.class);
  }

  @Test
  void player2_cannot_roll_at_start_of_game() {
    whereNextDiceRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_2))
        .expectException(IllegalStateException.class);
  }

  @Test
  void player2_can_can_roll_dice_if_first_turn_ended() {
    whereNextDiceRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_1))
        .andGiven(new TurnEnded(GAME_ID, ACCOUNT_PLAYER_2))
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_2))
        .expectEvents(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_2));
  }

  @Test
  void returns_all_sheep_from_all_players_if_the_bank_cannot_afford_payout() {
    whereNextDiceRollIs(4);
    fixture
        .given(getGameCreatedEvent())
        .andGiven(
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 7, 10, 0, 1)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 0, 1, 0, 1),
                new InventoryDTO(0, 10, 7, 0, 1)),
            new BankInventoryChanged(
                GAME_ID, new InventoryDTO(17, 17, 15, 17, 15), new InventoryDTO(17, 0, 0, 17, 15)))
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(
            new DiceRolled(GAME_ID, DiceRoll.FOUR, ACCOUNT_PLAYER_1),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_1,
                new InventoryDTO(0, 7, 10, 0, 1),
                new InventoryDTO(0, 0, 0, 0, 1)),
            new PlayerInventoryChanged(
                GAME_ID,
                ACCOUNT_PLAYER_2,
                new InventoryDTO(0, 10, 7, 0, 1),
                new InventoryDTO(0, 0, 0, 0, 1)),
            new BankInventoryChanged(
                GAME_ID, new InventoryDTO(17, 0, 0, 17, 15), new InventoryDTO(17, 17, 17, 17, 15)))
        .expectSuccessfulHandlerExecution();
  }
}
