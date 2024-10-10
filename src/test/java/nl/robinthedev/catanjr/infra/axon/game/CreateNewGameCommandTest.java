package nl.robinthedev.catanjr.infra.axon.game;

import nl.robinthedev.catanjr.api.command.CreateNewGame;
import org.junit.jupiter.api.Test;

class CreateNewGameCommandTest extends AbstractGameAggregateTest {

  @Test
  void creates_a_new_game() {
    fixture
        .givenNoPriorActivity()
        .when(new CreateNewGame(GAME_ID, ACCOUNT_PLAYER_1, JOHN, ACCOUNT_PLAYER_2, WICK))
        .expectEvents(getGameCreatedEvent())
        .expectSuccessfulHandlerExecution();
  }
}
