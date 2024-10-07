package nl.robinthedev.catanjr.infra.axon.projection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.infra.axon.persistence.TestInMemoryGames;
import org.junit.jupiter.api.Test;

class GameEventHandlerTest {

  private final TestInMemoryGames games = new TestInMemoryGames();
  private final GameEventHandler gameEventHandler = new GameEventHandler(games, new TestEmitter());

  @Test
  void gameGetsCreated() {
    var game = new GameDTO(null, null, null, null, null);
    var gameId = new GameId(UUID.randomUUID());

    gameEventHandler.on(new GameCreatedEvent(gameId, game));

    assertThat(games.get(gameId)).isEqualTo(game);
  }

  @Test
  void updatePlayerInventory_p1() {
    UUID p1Account = UUID.randomUUID();
    var game =
        new GameDTO(
            new PlayerDTO(p1Account, null, new InventoryDTO(0, 0, 0, 0, 0)),
            null,
            null,
            null,
            null);
    var gameId = new GameId(UUID.randomUUID());
    games.save(gameId, game);

    gameEventHandler.on(
        new PlayerInventoryChanged(gameId, p1Account, null, new InventoryDTO(1, 2, 3, 4, 5)));

    assertThat(games.get(gameId))
        .isEqualTo(
            new GameDTO(
                new PlayerDTO(p1Account, null, new InventoryDTO(1, 2, 3, 4, 5)),
                null,
                null,
                null,
                null));
  }

  @Test
  void updatePlayerInventory_p2() {
    UUID p1Account = UUID.randomUUID();
    UUID p2Account = UUID.randomUUID();
    var game =
        new GameDTO(
            new PlayerDTO(p1Account, null, new InventoryDTO(1, 2, 3, 4, 5)),
            new PlayerDTO(p2Account, null, new InventoryDTO(0, 0, 0, 0, 0)),
            null,
            null,
            null);
    var gameId = new GameId(UUID.randomUUID());
    games.save(gameId, game);

    gameEventHandler.on(
        new PlayerInventoryChanged(gameId, p2Account, null, new InventoryDTO(5, 4, 3, 2, 1)));

    assertThat(games.get(gameId))
        .isEqualTo(
            new GameDTO(
                new PlayerDTO(p1Account, null, new InventoryDTO(1, 2, 3, 4, 5)),
                new PlayerDTO(p2Account, null, new InventoryDTO(5, 4, 3, 2, 1)),
                null,
                null,
                null));
  }
}
