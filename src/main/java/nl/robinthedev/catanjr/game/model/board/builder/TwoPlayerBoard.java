package nl.robinthedev.catanjr.game.model.board.builder;

import static nl.robinthedev.catanjr.game.model.board.RequiredDiceRoll.*;
import static nl.robinthedev.catanjr.game.model.board.ResourceType.*;

import nl.robinthedev.catanjr.game.model.board.Board;
import nl.robinthedev.catanjr.game.model.board.Occupant;

public class TwoPlayerBoard {
  public static Board create() {
    var board = new BoardBuilder();
    board.addLandTile("sword1", SWORD, ONE);
    board.addLandTile("pineApple3", PINEAPPLE, THREE);
    board.addLandTile("sheep2", SHEEP, TWO);
    board.addLandTile("wood4", WOOD, FOUR);
    board.addLandTile("gold5", GOLD, FIVE);
    board.addLandTile("wood3", WOOD, THREE);
    board.addLandTile("sheep1", SHEEP, ONE);
    board.addLandTile("pineApple4", PINEAPPLE, FOUR);
    board.addLandTile("sword2", SWORD, TWO);

    board.surround("sword1", 1, 3, 2);
    board.surround("pineApple3", 1, 3, 5, 6, 4);
    board.surround("sheep2", 2, 3, 5, 8, 7);
    board.surround("wood4", 4, 6, 9, 10);
    board.surroundCircular("gold5", 5, 6, 9, 12, 11, 8);
    board.surround("wood3", 7, 8, 11, 13);
    board.surround("sheep1", 10, 9, 12, 14, 15);
    board.surround("pineApple4", 13, 11, 12, 14, 16);
    board.surround("sword2", 15, 14, 16);

    board.setOccupant(Occupant.PLAYER1, 2, 3);
    board.setOccupant(Occupant.PLAYER1, 13, 11);
    board.setOccupant(Occupant.PLAYER2, 4, 6);
    board.setOccupant(Occupant.PLAYER2, 15, 14);

    return board.build();
  }
}
