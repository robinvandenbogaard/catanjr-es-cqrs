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

  public Optional<Owner> ownerOf(SiteId shipSite) {
    var player = board.getOwner(shipSite);
    return Optional.ofNullable(
        switch (player) {
          case NONE, PLAYER3, PLAYER4 -> null;
          case PLAYER1 -> new Owner(firstPlayer().id());
          case PLAYER2 -> new Owner(secondPlayer().id());
        });
  }

  public DiceRollReport diceRolled(DiceRoll diceRoll) {
    var playerReports =
        players().stream()
            .map(
                player -> {
                  var currentInventory = player.inventory();
                  var gainedResources = board.getResources(diceRoll, player.nr());
                  return new PlayerReport(
                      player.accountId(),
                      currentInventory,
                      gainedResources,
                      currentInventory.add(gainedResources));
                })
            .toList();
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
}
