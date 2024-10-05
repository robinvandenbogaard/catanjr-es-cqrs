package nl.robinthedev.catanjr.api.command;

import nl.robinthedev.catanjr.api.dto.GameId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record CreateNewGame(
        @TargetAggregateIdentifier GameId gameId, UUID accountPlayer1, String usernamePlayer1, UUID accountPlayer2, String usernamePlayer2) {}
