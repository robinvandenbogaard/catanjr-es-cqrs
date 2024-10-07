package nl.robinthedev.catanjr.api.event;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;

public record TurnEnded(GameId gameId, UUID nextPlayer) {}
