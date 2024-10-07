package nl.robinthedev.catanjr.infra.axon.projection;

import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.query.GetGameQuery;
import nl.robinthedev.catanjr.game.service.Games;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

@Service
class GameEventHandler {

  private final Games games;
  private final QueryUpdateEmitter emitter;

  public GameEventHandler(Games games, QueryUpdateEmitter emitter) {
    this.games = games;
    this.emitter = emitter;
  }

  @EventHandler
  void on(GameCreatedEvent event) {
    games.save(event.gameId(), event.game());
    emitUpdate(event.gameId());
  }

  private void emitUpdate(GameId gameId) {
    emitter.emit(GetGameQuery.class, query -> query.gameId().equals(gameId), games.get(gameId));
  }

  @EventHandler
  void on(PlayerInventoryChanged event) {
    games.updatePlayerInventory(event.gameId(), event.accountPlayerId(), event.newInventory());
    emitUpdate(event.gameId());
  }

  @EventHandler
  public void on(BankInventoryChanged event) {
    games.updateBankInventory(event.gameId(), event.current());
    emitUpdate(event.gameId());
  }
}
