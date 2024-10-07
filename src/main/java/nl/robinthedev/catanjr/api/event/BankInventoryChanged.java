package nl.robinthedev.catanjr.api.event;

import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;

public record BankInventoryChanged(
    GameId gameId, InventoryDTO oldInventory, InventoryDTO newInventory) {}
