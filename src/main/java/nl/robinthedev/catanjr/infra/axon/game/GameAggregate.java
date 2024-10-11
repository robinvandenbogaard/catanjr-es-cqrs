package nl.robinthedev.catanjr.infra.axon.game;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.Set;
import java.util.stream.Collectors;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.ActionDTO;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.BuyShip;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.PlayerActionsChanged;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.ShipBought;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import nl.robinthedev.catanjr.game.model.Game;
import nl.robinthedev.catanjr.game.model.GameFactory;
import nl.robinthedev.catanjr.game.model.SiteId;
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
    apply(new PlayerActionsChanged(gameId, firstPlayer.accountId(), getCurrentPlayerActions()));
    apply(new PlayerActionsChanged(gameId, secondPlayer.accountId(), Set.of()));
  }

  @CommandHandler
  void handle(RollDice command, DiceRoller roller) {
    round.isAllowedToRoll(AccountId.of(command.accountPlayerId()));

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
    apply(new PlayerActionsChanged(gameId, event.accountPlayerId(), getCurrentPlayerActions()));
  }

  private Set<ActionDTO> getCurrentPlayerActions() {
    return round.actions().map(Enum::name).map(ActionDTO::valueOf).collect(Collectors.toSet());
  }

  @CommandHandler
  void handle(EndTurn command) {
    round.isAllowedToEndTurn(AccountId.of(command.accountPlayerId()));

    apply(new TurnEnded(gameId, round.nextPlayer()));
  }

  @EventSourcingHandler
  void on(TurnEnded event) {
    round = round.turnEnded(new AccountId(event.nextPlayer()));
    apply(new PlayerActionsChanged(gameId, round.currentPlayer(), getCurrentPlayerActions()));
    apply(new PlayerActionsChanged(gameId, round.nextPlayer(), Set.of()));
  }

  @CommandHandler
  void handle(BuyShip command) {
    AccountId playerAccountId = AccountId.of(command.playerAccountId());
    round.isAllowedToBuyShip(playerAccountId);

    game.buyShipYard(new SiteId(command.shipYardId()));

    apply(new ShipBought(gameId, round.currentPlayer(), new ShipYardDTO("3", OwnerDTO.PLAYER1)));
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
