package nl.robinthedev.catanjr.game.model;

import java.util.List;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public record BankReport(BankInventory currentInventory, BankInventory newInventory) {

  public static BankReport of(List<PlayerReport> playerReports, BankInventory bankInventory) {
    var allPlayerResourceChanges =
        playerReports.stream()
            .map(PlayerReport::resourceChanges)
            .reduce(ResourceChanges.EMPTY, ResourceChanges::add);
    var newBankInventory =
        bankInventory
            .minus(allPlayerResourceChanges.additions())
            .add(allPlayerResourceChanges.subtractions());
    return new BankReport(bankInventory, newBankInventory);
  }

  public boolean inventoryChanged() {
    return !currentInventory.equals(newInventory);
  }
}
