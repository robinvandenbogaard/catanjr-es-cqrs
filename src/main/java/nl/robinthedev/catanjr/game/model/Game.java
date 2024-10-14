package nl.robinthedev.catanjr.game.model;

import java.util.Objects;
import java.util.Optional;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.board.Board;
import nl.robinthedev.catanjr.game.model.coco.CocoTiles;
import nl.robinthedev.catanjr.game.model.player.AccountId;
import nl.robinthedev.catanjr.game.model.player.Player;
import nl.robinthedev.catanjr.game.model.player.Players;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.BuoyInventory;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public record Game(
    Players players,
    BuoyInventory buoyInventory,
    BankInventory bankInventory,
    CocoTiles cocoTiles,
    Board board) {
  public Game {
    Objects.requireNonNull(players);
    Objects.requireNonNull(buoyInventory);
    Objects.requireNonNull(bankInventory);
    Objects.requireNonNull(cocoTiles);
    Objects.requireNonNull(board);
  }

  public Player firstPlayer() {
    return players.firstPlayer();
  }

  public Player secondPlayer() {
    return players.secondPlayer();
  }

  public Optional<Owner> ownerOf(SiteId siteId) {
    var player = board.getFortOwner(siteId);
    return Optional.ofNullable(
        switch (player) {
          case NONE, PLAYER3, PLAYER4 -> null;
          case PLAYER1 -> new Owner(firstPlayer().id());
          case PLAYER2 -> new Owner(secondPlayer().id());
        });
  }

  public DiceRollReport diceRolled(DiceRoll diceRoll) {
    var payout =
        players().stream().map(player -> PlayerPayout.of(player, board, diceRoll)).toList();

    var allResourcesToTakeFrombank =
        payout
            .map(PlayerPayout::resourceChanges)
            .foldLeft(ResourceChanges.EMPTY, ResourceChanges::add);

    var exceeded = bankInventory.getExceedingResources(allResourcesToTakeFrombank);

    var playerReports =
        payout.map(p -> p.confiscate(exceeded)).map(PlayerPayout::asReport).toList();
    var bankReport = BankReport.of(playerReports, bankInventory);
    return new DiceRollReport(playerReports, bankReport);
  }

  public Game updateIventory(AccountId accountId, PlayerInventory inventory) {
    return new Game(
        players.updateInventory(accountId, inventory),
        buoyInventory,
        bankInventory,
        cocoTiles,
        board);
  }

  public Game updateBankIventory(BankInventory bankInventory) {
    return new Game(players, buoyInventory, bankInventory, cocoTiles, board);
  }

  public void canBuyFortAt(Player player, SiteId siteId) {
    board.mustBeUnoccupied(siteId);
    board.mustHaveAdjecentShip(siteId, player.nr());
    player.mustBeAbleToPay(ResourceChanges.FORT);
  }

  public Game playerBought(Player player, SiteId siteId) {
    return new Game(
        players, buoyInventory, bankInventory, cocoTiles, board.markFortOwned(siteId, player.nr()));
  }

  public Player getPlayer(AccountId id) {
    if (firstPlayer().belongsTo(id)) return firstPlayer();
    else return secondPlayer();
  }

  public void canBuyShipAt(Player player, ShipId shipId) {
    board.mustBeUnoccupied(shipId);
    board.mustHaveAdjecentFort(shipId, player.nr());
    player.mustBeAbleToPay(ResourceChanges.SHIP);
  }

  public Game playerBought(Player player, ShipId shipId) {
    return new Game(
        players, buoyInventory, bankInventory, cocoTiles, board.markShipOwned(shipId, player.nr()));
  }
}
