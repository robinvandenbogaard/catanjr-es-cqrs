package nl.robinthedev.catanjr.api.command;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record BuyShip(@TargetAggregateIdentifier GameId gameId, UUID playerAccountId, String shipYardId) {}
