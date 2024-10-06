package nl.robinthedev.catanjr.api.dto;

public enum DiceRoll {
  ONE;

  public static DiceRoll valueOf(int value) {
    return switch (value) {
      case 1 -> ONE;
      default -> throw new IllegalArgumentException("Only value of 1 to 6 is allowed.");
    };
  }
}
