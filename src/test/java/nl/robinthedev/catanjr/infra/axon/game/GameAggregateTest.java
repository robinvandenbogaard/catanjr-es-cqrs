package nl.robinthedev.catanjr.infra.axon.game;

import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.BuoyDTO;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameAggregateTest {
  public static final GameId GAME_ID = GameId.randomGameId();
  public static final UUID ACCOUNT_PLAYER_1 = UUID.randomUUID();
  public static final UUID ACCOUNT_PLAYER_2 = UUID.randomUUID();
  public static final String JOHN = "John";
  public static final String WICK = "Wick";
  private static final List<ShipYardDTO> INITIAL_COLOR_MAP;
  private AggregateTestFixture<GameAggregate> fixture;

  static {
    INITIAL_COLOR_MAP = new ArrayList<>();
    INITIAL_COLOR_MAP.add(new ShipYardDTO("1", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("2", OwnerDTO.PLAYER1));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("3", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("4", OwnerDTO.PLAYER2));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("5", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("6", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("7", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("8", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("9", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("10", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("11", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("12", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("13", OwnerDTO.PLAYER1));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("14", OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("15", OwnerDTO.PLAYER2));
    INITIAL_COLOR_MAP.add(new ShipYardDTO("16", OwnerDTO.NONE));
  }

  private ManipulatableDiceRoller diceRoller;

  @BeforeEach
  void setUp() {
    fixture = new AggregateTestFixture<>(GameAggregate.class);
    diceRoller = new ManipulatableDiceRoller();
    fixture.registerInjectableResource(diceRoller);
  }

  @Test
  void creates_a_new_game() {
    fixture
        .givenNoPriorActivity()
        .when(new CreateNewGame(GAME_ID, ACCOUNT_PLAYER_1, JOHN, ACCOUNT_PLAYER_2, WICK))
        .expectEvents(getGameCreatedEvent())
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void first_player_rolls_1() {
    diceRoller.nextRollIs(1);
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
    diceRoller.nextRollIs(2);
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
    diceRoller.nextRollIs(3);
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
    diceRoller.nextRollIs(4);
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
    diceRoller.nextRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectEvents(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_1))
        .expectSuccessfulHandlerExecution();
  }

  @Test
  void cannot_roll_multiple_times_for_the_same_player() {
    diceRoller.nextRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_1))
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_1))
        .expectException(IllegalStateException.class);
  }

  @Test
  void player2_cannot_roll_at_start_of_game() {
    diceRoller.nextRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_2))
        .expectException(IllegalStateException.class);
  }

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

  @Test
  void player2_can_can_roll_dice_if_first_turn_ended() {
    diceRoller.nextRollIs(5);
    fixture
        .given(getGameCreatedEvent())
        .andGiven(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_1))
        .andGiven(new TurnEnded(GAME_ID, ACCOUNT_PLAYER_2))
        .when(new RollDice(GAME_ID, ACCOUNT_PLAYER_2))
        .expectEvents(new DiceRolled(GAME_ID, DiceRoll.FIVE, ACCOUNT_PLAYER_2));
  }

  @Test
  void returns_all_sheep_from_all_players_if_the_bank_cannot_afford_payout() {
    diceRoller.nextRollIs(4);
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

  private static GameCreatedEvent getGameCreatedEvent() {
    return new GameCreatedEvent(
        GAME_ID,
        new GameDTO(
            new PlayerDTO(ACCOUNT_PLAYER_1, JOHN, new InventoryDTO(0, 0, 1, 0, 1)),
            new PlayerDTO(ACCOUNT_PLAYER_2, WICK, new InventoryDTO(0, 0, 1, 0, 1)),
            new InventoryDTO(17, 17, 15, 17, 15),
            List.of(
                new BuoyDTO(WOOD),
                new BuoyDTO(GOLD),
                new BuoyDTO(PINEAPPLE),
                new BuoyDTO(SHEEP),
                new BuoyDTO(SWORD)),
            INITIAL_COLOR_MAP));
  }
}
