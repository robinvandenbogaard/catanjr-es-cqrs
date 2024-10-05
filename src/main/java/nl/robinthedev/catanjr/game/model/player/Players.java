package nl.robinthedev.catanjr.game.model.player;

import java.util.List;
import java.util.Objects;

public record Players(List<Player> players) {
  public Players {
    Objects.requireNonNull(players);
    if (players.size() != 2) {
      throw new IllegalArgumentException("Players list must contain exactly two players");
    }
  }

  public Player firstPlayer() {
    return players.get(0);
  }

  public Player secondPlayer() {
    return players.get(1);
  }
}
