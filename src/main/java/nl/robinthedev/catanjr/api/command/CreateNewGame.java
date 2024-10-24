package nl.robinthedev.catanjr.api.command;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateNewGame(
    @TargetAggregateIdentifier GameId gameId,
    String title,
    UUID accountPlayer1,
    String usernamePlayer1,
    UUID accountPlayer2,
    String usernamePlayer2) {}
