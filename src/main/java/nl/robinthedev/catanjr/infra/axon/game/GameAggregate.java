package nl.robinthedev.catanjr.infra.axon.game;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import nl.robinthedev.catanjr.api.command.BuyFort;
import nl.robinthedev.catanjr.api.command.BuyShip;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.ActionDTO;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
import nl.robinthedev.catanjr.api.event.BankInventoryChanged;
import nl.robinthedev.catanjr.api.event.DiceRolled;
import nl.robinthedev.catanjr.api.event.FortBought;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import nl.robinthedev.catanjr.api.event.PlayerActionsChanged;
import nl.robinthedev.catanjr.api.event.PlayerInventoryChanged;
import nl.robinthedev.catanjr.api.event.ShipBought;
import nl.robinthedev.catanjr.api.event.TurnEnded;
import nl.robinthedev.catanjr.game.model.Game;
import nl.robinthedev.catanjr.game.model.GameFactory;
import nl.robinthedev.catanjr.game.model.ShipId;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.player.AccountId;
import nl.robinthedev.catanjr.game.model.player.Player;
import nl.robinthedev.catanjr.game.model.player.PlayerId;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;
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

  Game game;
  Round round;

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
    apply(new PlayerActionsChanged(gameId, secondPlayer.accountId(), HashSet.empty()));
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

  private PlayerInventory toPlayerInventory(InventoryDTO inventory) {
    return PlayerInventory.of(
        inventory.wood(),
        inventory.gold(),
        inventory.pineApple(),
        inventory.sheep(),
        inventory.sword());
  }

  @EventSourcingHandler
  void on(BankInventoryChanged event) {
    game = game.updateBankIventory(toBankInventory(event.newInventory()));
  }

  private BankInventory toBankInventory(InventoryDTO inventory) {
    return BankInventory.of(
        inventory.wood(),
        inventory.gold(),
        inventory.pineApple(),
        inventory.sheep(),
        inventory.sword());
  }

  @EventSourcingHandler
  void on(DiceRolled event) {
    round = round.diceRolled();
    apply(new PlayerActionsChanged(gameId, event.accountPlayerId(), getCurrentPlayerActions()));
  }

  private Set<ActionDTO> getCurrentPlayerActions() {
    return round.actions().map(Enum::name).map(ActionDTO::valueOf).toSet();
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
    apply(new PlayerActionsChanged(gameId, round.nextPlayer(), HashSet.of()));
  }

  @CommandHandler
  void handle(BuyFort command) {
    AccountId playerAccountId = AccountId.of(command.playerAccountId());
    round.isAllowedToBuyFort(playerAccountId);

    game.canBuyFortAt(getCurrentPlayer(), new SiteId(command.siteId()));

    apply(
        new FortBought(
            gameId,
            round.currentPlayer(),
            new FortSiteDTO(
                command.siteId(), OwnerDTO.from(game.getPlayer(playerAccountId).nr()))));
    apply(
        new PlayerInventoryChanged(
            gameId,
            round.currentPlayer(),
            InventoryDTO.of(getCurrentPlayer().inventory()),
            InventoryDTO.of(getCurrentPlayer().inventory().minus(ResourceChanges.FORT))));
    apply(
        new BankInventoryChanged(
            gameId,
            InventoryDTO.of(game.bankInventory()),
            InventoryDTO.of(game.bankInventory().add(ResourceChanges.FORT))));
  }

  private Player getCurrentPlayer() {
    return game.getPlayer(new AccountId(round.currentPlayer()));
  }

  @EventSourcingHandler
  void on(FortBought event) {
    game = game.playerBought(getCurrentPlayer(), new SiteId(event.boughtFortAt().id()));
  }

  @CommandHandler
  void handle(BuyShip command) {
    AccountId playerAccountId = AccountId.of(command.playerAccountId());
    round.isAllowedToBuyShip(playerAccountId);
    game.canBuyShipAt(new ShipId(command.shipYardId()));

    apply(
        new ShipBought(
            gameId,
            command.playerAccountId(),
            new ShipYardDTO(
                command.shipYardId(), OwnerDTO.from(game.getPlayer(playerAccountId).nr()))));
  }

  @EventSourcingHandler
  void on(ShipBought event) {
    game = game.playerBought(getCurrentPlayer(), new ShipId(event.boughtShipAt().id()));
  }
}
