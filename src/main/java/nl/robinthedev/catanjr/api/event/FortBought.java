package nl.robinthedev.catanjr.api.event;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.GameId;

public record FortBought(GameId gameId, UUID playerAccountId, FortSiteDTO boughtFortAt) {}
