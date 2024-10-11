package nl.robinthedev.catanjr.game.model;

import java.util.List;
import nl.robinthedev.catanjr.game.model.board.BoardPlayer;
import nl.robinthedev.catanjr.game.model.board.builder.TwoPlayerBoard;
import nl.robinthedev.catanjr.game.model.coco.CocoTiles;
import nl.robinthedev.catanjr.game.model.player.Player;
import nl.robinthedev.catanjr.game.model.player.PlayerId;
import nl.robinthedev.catanjr.game.model.player.Players;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.BuoyInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public class GameFactory {
  public static Game of(PlayerId playerOne, PlayerId playerTwo) {
    Player player1 = Player.of(playerOne, BoardPlayer.PLAYER1);
    Player player2 = Player.of(playerTwo, BoardPlayer.PLAYER2);
    var players = new Players(List.of(player1, player2));
    var buoyInventory = BuoyInventory.INITIAL;
    var board = TwoPlayerBoard.create();

    return new Game(
        players,
        buoyInventory,
        BankInventory.FULL
            .minus(ResourceChanges.allOf(player1.inventory()))
            .minus(ResourceChanges.allOf(player2.inventory()))
            .minus(ResourceChanges.allOf(buoyInventory)),
        CocoTiles.INITIAL,
        board);
  }
}
