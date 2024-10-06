package nl.robinthedev.catanjr.game.model;

import java.util.List;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.GainedResources;

public record BankReport(BankInventory currentInventory, BankInventory newInventory) {

  public static BankReport of(List<PlayerReport> playerReports, BankInventory bankInventory) {
    var resourcesToTakeFrombank =
        playerReports.stream()
            .map(PlayerReport::gainedResources)
            .reduce(GainedResources.EMPTY, GainedResources::add);
    var newBankInventory = bankInventory.minus(resourcesToTakeFrombank);
    return new BankReport(bankInventory, newBankInventory);
  }

  public boolean inventoryChanged() {
    return !currentInventory.equals(newInventory);
  }
}
