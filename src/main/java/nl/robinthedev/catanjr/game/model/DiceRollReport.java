package nl.robinthedev.catanjr.game.model;

import java.util.List;
import java.util.function.Consumer;

public class DiceRollReport {
  private final List<PlayerReport> playerReports;

  DiceRollReport(List<PlayerReport> playerReports) {
    this.playerReports = playerReports;
  }

  public void forPlayers(Consumer<PlayerReport> consumer) {
    playerReports.stream().filter(PlayerReport::inventoryChanged).forEach(consumer);
  }

  @Override
  public String toString() {
    return "DiceRollReport[" + "playerReports=" + playerReports + ']';
  }
}
