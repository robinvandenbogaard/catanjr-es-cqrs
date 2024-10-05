package nl.robinthedev.catanjr.game.model;

import java.util.Objects;
import java.util.Optional;
import nl.robinthedev.catanjr.game.model.board.Board;
import nl.robinthedev.catanjr.game.model.coco.CocoTiles;
import nl.robinthedev.catanjr.game.model.player.Player;
import nl.robinthedev.catanjr.game.model.player.Players;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.BuoyInventory;

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
}
