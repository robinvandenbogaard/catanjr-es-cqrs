package nl.robinthedev.catanjr.infra.axon.game;

import java.util.UUID;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameAggregateTest {
  public static final GameId GAME_ID = GameId.randomGameId();
  public static final UUID ACCOUNT_PLAYER_1 = UUID.randomUUID();
  public static final UUID ACCOUNT_PLAYER_2 = UUID.randomUUID();
  public static final String JOHN = "John";
  public static final String WICK = "Wick";
  private AggregateTestFixture<GameAggregate> fixture;

  @BeforeEach
  void setUp() {
    fixture = new AggregateTestFixture<>(GameAggregate.class);
  }

  @Test
  void creates_a_new_game() {
    fixture
        .givenNoPriorActivity()
        .when(new CreateNewGame(GAME_ID, ACCOUNT_PLAYER_1, JOHN, ACCOUNT_PLAYER_2, WICK))
        .expectEvents(getGameCreatedEvent())
        .expectSuccessfulHandlerExecution();
  }

  private static GameCreatedEvent getGameCreatedEvent() {
    return new GameCreatedEvent(
        GAME_ID,
        new GameDTO(
            new PlayerDTO(ACCOUNT_PLAYER_1, JOHN, new InventoryDTO(0, 0, 1, 0, 1)),
            new PlayerDTO(ACCOUNT_PLAYER_2, WICK, new InventoryDTO(0, 0, 1, 0, 1)),
            new InventoryDTO(17, 17, 15, 17, 15),
            new InventoryDTO(1, 1, 1, 1, 1)));
  }
}
