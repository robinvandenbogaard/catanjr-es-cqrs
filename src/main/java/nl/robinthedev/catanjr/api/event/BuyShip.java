package nl.robinthedev.catanjr.api.event;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record BuyShip(
    @TargetAggregateIdentifier GameId gameId, UUID playerAccountId, int shipYardId) {}
