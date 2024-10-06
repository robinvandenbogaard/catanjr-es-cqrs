package nl.robinthedev.catanjr.api.dto;

public enum DiceRoll {
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE;

  public static DiceRoll valueOf(int value) {
    return switch (value) {
      case 1 -> ONE;
      case 2 -> TWO;
      case 3 -> THREE;
      case 4 -> FOUR;
      case 5 -> FIVE;
      default -> throw new IllegalArgumentException("Invalid value: " + value);
    };
  }
}
