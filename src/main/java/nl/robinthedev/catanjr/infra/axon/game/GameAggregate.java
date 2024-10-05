package nl.robinthedev.catanjr.infra.axon.game;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.game.model.Game;
import nl.robinthedev.catanjr.game.model.GameFactory;
import nl.robinthedev.catanjr.game.model.player.PlayerId;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class GameAggregate {

  @AggregateIdentifier
  GameId gameId;

  private Game game;

  public GameAggregate() {
    // Required by axon
  }

  @CommandHandler
  @CreationPolicy(AggregateCreationPolicy.ALWAYS)
  void handle(CreateNewGame command) {
    var game = GameFactory.of(PlayerId.from(command.accountPlayer1(), command.usernamePlayer1()), PlayerId.from(command.accountPlayer2(), command.usernamePlayer2()));
    apply(new GameCreatedEvent(command.gameId(), GameDTO.of(game)));
  }

  @EventSourcingHandler
  void on(GameCreatedEvent event) {
    this.gameId = event.gameId();
    PlayerDTO firstPlayer = event.game().firstPlayer();
    PlayerDTO secondPlayer = event.game().secondPlayer();
    this.game =
        GameFactory.of(
            PlayerId.from(firstPlayer.accountId(), firstPlayer.username()),
            PlayerId.from(secondPlayer.accountId(), secondPlayer.username()));
    ;
  }
}
