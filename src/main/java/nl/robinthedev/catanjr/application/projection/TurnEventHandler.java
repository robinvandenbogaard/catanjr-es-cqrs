package nl.robinthedev.catanjr.application.projection;

import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import nl.robinthedev.catanjr.game.service.PlayerTurn;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
class TurnEventHandler {
  private final PlayerTurn turns;

  public TurnEventHandler(PlayerTurn turns) {
    this.turns = turns;
  }

  @EventHandler
  void on(GameCreatedEvent event) {
    turns.currentPlayerChangedTo(event.gameId(), event.game().firstPlayer().accountId());
  }

  @EventHandler
  void on(TurnEnded event) {
    turns.currentPlayerChangedTo(event.gameId(), event.nextPlayer());
  }
}
