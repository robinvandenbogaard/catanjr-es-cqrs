package nl.robinthedev.catanjr.game.model;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.collection.List;
import java.util.UUID;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;
import org.junit.jupiter.api.Test;

class BankReportTest {

  @Test
  void noPlayerReportsNoChanges() {
    var bankReport = BankReport.of(List.of(), BankInventory.FULL);
    assertThat(bankReport.inventoryChanged()).isFalse();
  }

  @Test
  void removesResourcesFromPlayerReports() {
    var playerReport =
        new PlayerReport(
            UUID.randomUUID(),
            PlayerInventory.INITIAL,
            ResourceChanges.wood(1),
            PlayerInventory.of(2, 0, 0, 0, 1));
    var bankReport = BankReport.of(List.of(playerReport), BankInventory.FULL);
    assertThat(bankReport.inventoryChanged()).isTrue();
    assertThat(bankReport)
        .isEqualTo(new BankReport(BankInventory.FULL, BankInventory.of(17, 18, 18, 18, 18)));
  }

  @Test
  void removesResourcesFromAllPlayerReports() {
    var playerReport1 =
        new PlayerReport(
            UUID.randomUUID(),
            PlayerInventory.INITIAL,
            ResourceChanges.wood(1),
            PlayerInventory.of(2, 0, 0, 0, 1));
    var playerReport2 =
        new PlayerReport(
            UUID.randomUUID(),
            PlayerInventory.INITIAL,
            ResourceChanges.sheep(1),
            PlayerInventory.of(2, 0, 0, 0, 1));
    var bankReport = BankReport.of(List.of(playerReport1, playerReport2), BankInventory.FULL);
    assertThat(bankReport.inventoryChanged()).isTrue();
    assertThat(bankReport)
        .isEqualTo(new BankReport(BankInventory.FULL, BankInventory.of(17, 18, 18, 17, 18)));
  }

  @Test
  void inventoryDidNotChange() {
    var bankReport = new BankReport(BankInventory.FULL, BankInventory.FULL);
    assertThat(bankReport.inventoryChanged()).isFalse();
  }

  @Test
  void inventoryChanged() {
    var bankReport = new BankReport(BankInventory.FULL, BankInventory.EMPTY);
    assertThat(bankReport.inventoryChanged()).isTrue();
  }
}
