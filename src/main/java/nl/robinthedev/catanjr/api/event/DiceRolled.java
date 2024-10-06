package nl.robinthedev.catanjr.api.event;

import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.api.dto.GameId;

public record DiceRolled(GameId gameId, DiceRoll diceRoll, UUID accountPlayerId) {}
