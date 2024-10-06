package nl.robinthedev.catanjr.game.model;

import java.util.List;
import nl.robinthedev.catanjr.game.model.board.BoardPlayer;
import nl.robinthedev.catanjr.game.model.board.TwoPlayerBoard;
import nl.robinthedev.catanjr.game.model.coco.CocoTiles;
import nl.robinthedev.catanjr.game.model.player.Player;
import nl.robinthedev.catanjr.game.model.player.PlayerId;
import nl.robinthedev.catanjr.game.model.player.Players;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.BuoyInventory;

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
            .minus(player1.inventory())
            .minus(player2.inventory())
            .minus(buoyInventory),
        CocoTiles.INITIAL,
        board);
  }
}
