package nl.robinthedev.catanjr.game.model.board.builder;

import static nl.robinthedev.catanjr.game.model.board.RequiredDiceRoll.*;
import static nl.robinthedev.catanjr.game.model.board.ResourceType.*;

import nl.robinthedev.catanjr.game.model.board.Board;
import nl.robinthedev.catanjr.game.model.board.Occupant;

public class FourPlayerBoard {
  public static Board create() {
    var board = new BoardBuilder();
    board.addLandTile("sheep5", SHEEP, FIVE);
    board.addLandTile("pineApple2", PINEAPPLE, TWO);
    board.addLandTile("pineApple4", PINEAPPLE, FOUR);
    board.addLandTile("gold3", GOLD, THREE);
    board.addLandTile("sheep1", SHEEP, ONE);
    board.addLandTile("sheep2", SHEEP, TWO);
    board.addLandTile("wood2", WOOD, TWO);
    board.addLandTile("wood1", WOOD, ONE);
    board.addLandTile("gold5", GOLD, FIVE);
    board.addLandTile("sword1", SWORD, ONE);
    board.addLandTile("sword4", SWORD, FOUR);
    board.addLandTile("wood3", WOOD, THREE);

    board.surroundCircular("sheep5", 1, 2, 4, 6, 5, 3);
    board.surround("pineApple2", 3, 5, 8, 7);
    board.surround("pineApple4", 4, 6, 9, 10);
    board.surroundCircular("gold3", 5, 6, 9, 13, 12, 8);
    board.surroundCircular("sheep1", 7, 8, 12, 16, 15, 11);
    board.surroundCircular("sheep2", 9, 10, 14, 18, 17, 13);
    board.surroundCircular("wood2", 15, 16, 20, 24, 23, 19);
    board.surroundCircular("wood1", 17, 18, 22, 26, 25, 21);
    board.surroundCircular("gold5", 20, 21, 25, 28, 27, 24);
    board.surround("sword1", 23, 24, 27, 29);
    board.surround("sword4", 26, 25, 28, 30);
    board.surroundCircular("wood3", 27, 28, 30, 32, 31, 29);

    board.setOccupant(Occupant.PLAYER1, 7, 11);
    board.setOccupant(Occupant.PLAYER1, 30, 28);
    board.setOccupant(Occupant.PLAYER2, 3, 5);
    board.setOccupant(Occupant.PLAYER2, 26, 22);
    board.setOccupant(Occupant.PLAYER3, 4, 6);
    board.setOccupant(Occupant.PLAYER3, 23, 19);
    board.setOccupant(Occupant.PLAYER4, 10, 14);
    board.setOccupant(Occupant.PLAYER4, 29, 27);

    return board.build();
  }
}
