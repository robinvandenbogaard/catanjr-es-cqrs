package nl.robinthedev.catanjr.infra.axon.game;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import nl.robinthedev.catanjr.game.model.Game;
import nl.robinthedev.catanjr.game.model.GameFactory;
import nl.robinthedev.catanjr.game.model.player.AccountId;
import nl.robinthedev.catanjr.game.model.player.PlayerId;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import nl.robinthedev.catanjr.game.model.round.Round;
import nl.robinthedev.catanjr.game.service.DiceRoller;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class GameAggregate {

  @AggregateIdentifier GameId gameId;

  private Game game;
  private Round round;

  public GameAggregate() {
    // Required by axon
  }

  @CommandHandler
  @CreationPolicy(AggregateCreationPolicy.ALWAYS)
  void handle(CreateNewGame command) {
    var game =
        GameFactory.of(
            PlayerId.from(command.accountPlayer1(), command.usernamePlayer1()),
            PlayerId.from(command.accountPlayer2(), command.usernamePlayer2()));
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
    this.round =
        Round.firstRound(
            AccountId.of(firstPlayer.accountId()), AccountId.of(secondPlayer.accountId()));
  }

  @CommandHandler
  void handle(RollDice command, DiceRoller roller) {
    if (!round.allowedToRoll(AccountId.of(command.accountPlayerId())))
      throw new IllegalStateException("You are not allowed to make a dice roll in this round.");

    var diceRoll = roller.roll();
    var diceRollReport = game.diceRolled(diceRoll);

    apply(new DiceRolled(gameId, diceRoll, command.accountPlayerId()));

    diceRollReport.forPlayers(
        playerReport ->
            apply(
                new PlayerInventoryChanged(
                    gameId,
                    playerReport.accountId(),
                    InventoryDTO.of(playerReport.currentInventory()),
                    InventoryDTO.of(playerReport.newInventory()))));

    diceRollReport.forBank(
        bankReport ->
            apply(
                new BankInventoryChanged(
                    gameId,
                    InventoryDTO.of(bankReport.currentInventory()),
                    InventoryDTO.of(bankReport.newInventory()))));
  }

  @EventSourcingHandler
  void on(PlayerInventoryChanged event) {
    game =
        game.updateIventory(
            AccountId.of(event.accountPlayerId()), toPlayerInventory(event.newInventory()));
  }

  @EventSourcingHandler
  void on(BankInventoryChanged event) {
    game = game.updateBankIventory(toBankInventory(event.newInventory()));
  }

  @EventSourcingHandler
  void on(DiceRolled event) {
    round = round.diceRolled();
  }

  @CommandHandler
  void handle(EndTurn command) {
    if (!round.allowedToEndTurn(AccountId.of(command.accountPlayerId())))
      throw new IllegalStateException(
          "You are not allowed to end your turn yet, please roll first.");

    apply(new TurnEnded(gameId, round.nextPlayer()));
  }

  @EventSourcingHandler
  void on(TurnEnded event) {
    round = round.turnEnded(new AccountId(event.nextPlayer()));
  }

  private PlayerInventory toPlayerInventory(InventoryDTO inventory) {
    return PlayerInventory.of(
        inventory.wood(),
        inventory.gold(),
        inventory.pineApple(),
        inventory.sheep(),
        inventory.sword());
  }

  private BankInventory toBankInventory(InventoryDTO inventory) {
    return BankInventory.of(
        inventory.wood(),
        inventory.gold(),
        inventory.pineApple(),
        inventory.sheep(),
        inventory.sword());
  }
}
