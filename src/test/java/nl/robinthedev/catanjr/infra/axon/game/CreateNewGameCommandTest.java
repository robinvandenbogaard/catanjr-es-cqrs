package nl.robinthedev.catanjr.infra.axon.game;

import io.vavr.collection.HashSet;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.dto.ActionDTO;
import nl.robinthedev.catanjr.api.event.PlayerActionsChanged;
import org.junit.jupiter.api.Test;

class CreateNewGameCommandTest extends AbstractGameAggregateTest {

  @Test
  void creates_a_new_game() {
    fixture
        .givenNoPriorActivity()
        .when(
            new CreateNewGame(GAME_ID, GAME_TITLE, ACCOUNT_PLAYER_1, JOHN, ACCOUNT_PLAYER_2, WICK))
        .expectEvents(
            getGameCreatedEvent(),
            new PlayerActionsChanged(GAME_ID, ACCOUNT_PLAYER_1, HashSet.of(ActionDTO.THROW_DICE)),
            new PlayerActionsChanged(GAME_ID, ACCOUNT_PLAYER_2, HashSet.of()))
        .expectSuccessfulHandlerExecution();
  }
}
