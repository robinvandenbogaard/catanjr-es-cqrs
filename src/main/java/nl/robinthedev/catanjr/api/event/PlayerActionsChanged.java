package nl.robinthedev.catanjr.api.event;

import io.vavr.collection.Set;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.ActionDTO;
import nl.robinthedev.catanjr.api.dto.GameId;

public record PlayerActionsChanged(GameId gameId, UUID playerAccountId, Set<ActionDTO> actions) {}
