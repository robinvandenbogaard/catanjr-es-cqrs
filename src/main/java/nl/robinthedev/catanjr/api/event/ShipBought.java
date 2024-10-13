package nl.robinthedev.catanjr.api.event;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;

public record ShipBought(GameId gameId, UUID playerAccountId, ShipYardDTO boughtShipAt) {}
