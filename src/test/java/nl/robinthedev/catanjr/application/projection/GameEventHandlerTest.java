package nl.robinthedev.catanjr.application.projection;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.collection.HashSet;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.infra.persistence.TestInMemoryGames;
import org.junit.jupiter.api.Test;

class GameEventHandlerTest {

  private final TestInMemoryGames games = new TestInMemoryGames();
  private final GameEventHandler gameEventHandler = new GameEventHandler(games, new TestEmitter());

  @Test
  void gameGetsCreated() {
    var game = new GameDTO(null, null, null, null, null, null);
    var gameId = new GameId(UUID.randomUUID());

    gameEventHandler.on(new GameCreatedEvent(gameId, game));

    assertThat(games.get(gameId)).isEqualTo(game);
  }

  @Test
  void updatePlayerInventory_p1() {
    UUID p1Account = UUID.randomUUID();
    var game =
        new GameDTO(
            new PlayerDTO(p1Account, null, new InventoryDTO(0, 0, 0, 0, 0), HashSet.of()),
            null,
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
                new PlayerDTO(p1Account, null, new InventoryDTO(1, 2, 3, 4, 5), HashSet.of()),
                null,
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
            new PlayerDTO(p1Account, null, new InventoryDTO(1, 2, 3, 4, 5), HashSet.of()),
            new PlayerDTO(p2Account, null, new InventoryDTO(0, 0, 0, 0, 0), HashSet.of()),
            null,
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
                new PlayerDTO(p1Account, null, new InventoryDTO(1, 2, 3, 4, 5), HashSet.of()),
                new PlayerDTO(p2Account, null, new InventoryDTO(5, 4, 3, 2, 1), HashSet.of()),
                null,
                null,
                null,
                null));
  }

  @Test
  void updateBankInventory() {
    var game = new GameDTO(null, null, new InventoryDTO(18, 18, 18, 18, 18), null, null, null);
    var gameId = new GameId(UUID.randomUUID());
    games.save(gameId, game);

    gameEventHandler.on(
        new BankInventoryChanged(gameId, null, new InventoryDTO(16, 16, 16, 16, 16)));

    assertThat(games.get(gameId))
        .isEqualTo(new GameDTO(null, null, new InventoryDTO(16, 16, 16, 16, 16), null, null, null));
  }
}
