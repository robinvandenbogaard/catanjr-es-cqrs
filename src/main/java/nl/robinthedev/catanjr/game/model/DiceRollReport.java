package nl.robinthedev.catanjr.game.model;

import java.util.List;
import java.util.function.Consumer;

public class DiceRollReport {
  private final List<PlayerReport> playerReports;
  private final BankReport bankReport;

  DiceRollReport(List<PlayerReport> playerReports, BankReport bankReport) {
    this.playerReports = playerReports;
    this.bankReport = bankReport;
  }

  public void forPlayers(Consumer<PlayerReport> consumer) {
    playerReports.stream().filter(PlayerReport::inventoryChanged).forEach(consumer);
  }

  public void forBank(Consumer<BankReport> consumer) {
    if (bankReport.inventoryChanged()) {
      consumer.accept(bankReport);
    }
  }
}
