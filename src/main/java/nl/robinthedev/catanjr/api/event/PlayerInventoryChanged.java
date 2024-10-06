package nl.robinthedev.catanjr.api.event;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;

public record PlayerInventoryChanged(
    GameId gameId, UUID accountPlayerId, InventoryDTO oldInventory, InventoryDTO newInventory) {}
