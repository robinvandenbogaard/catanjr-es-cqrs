package nl.robinthedev.catanjr.api.event;

import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;

public record GameCreatedEvent(GameId gameId, GameDTO game) {}
