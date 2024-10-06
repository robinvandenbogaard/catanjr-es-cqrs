package nl.robinthedev.catanjr.api.command;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RollDice(@TargetAggregateIdentifier GameId gameId, UUID accountPlayerId) {}
