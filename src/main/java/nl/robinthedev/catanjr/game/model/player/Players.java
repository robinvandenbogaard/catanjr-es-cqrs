package nl.robinthedev.catanjr.game.model.player;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;

public class Players {
  private final List<Player> players;

  public Players(List<Player> players) {
    this.players = Objects.requireNonNull(players);
    if (players.size() != 2) {
      throw new IllegalArgumentException("Players list must contain exactly two players");
    }
  }

  public Player firstPlayer() {
    return players.getFirst();
  }

  public Player secondPlayer() {
    return players.get(1);
  }

  public Stream<Player> stream() {
    return players.stream();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Players players1 = (Players) o;
    return players.equals(players1.players);
  }

  @Override
  public int hashCode() {
    return players.hashCode();
  }

  public Players updateInventory(AccountId accountId, PlayerInventory inventory) {
    var updatedPlayers =
        players.stream()
            .map(
                player -> {
                  if (player.belongsTo(accountId)) {
                    return player.setInventory(inventory);
                  }
                  return player;
                })
            .toList();

    return new Players(updatedPlayers);
  }
}
