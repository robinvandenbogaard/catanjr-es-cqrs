package nl.robinthedev.catanjr.game.model.resources;

import java.util.Objects;

public record ResourceChanges(
    Integer wood, Integer gold, Integer pineApple, Integer sheep, Integer sword) {

  public static final ResourceChanges EMPTY = ResourceChanges.of(0, 0, 0, 0, 0);
  public static final ResourceChanges FORT = ResourceChanges.of(1, 0, 1, 1, 1);
  public static final ResourceChanges SHIP = ResourceChanges.of(1, 0, 0, 1, 0);

  public ResourceChanges {
    Objects.requireNonNull(wood);
    Objects.requireNonNull(gold);
    Objects.requireNonNull(pineApple);
    Objects.requireNonNull(sheep);
    Objects.requireNonNull(sword);
  }

  public static ResourceChanges of(int wood, int gold, int pineApple, int sheep, int sword) {
    return new ResourceChanges(wood, gold, pineApple, sheep, sword);
  }

  public static ResourceChanges swords(int swords) {
    return ResourceChanges.of(0, 0, 0, 0, swords);
  }

  public static ResourceChanges gold(int gold) {
    return ResourceChanges.of(0, gold, 0, 0, 0);
  }

  public static ResourceChanges pineApple(int pineApple) {
    return ResourceChanges.of(0, 0, pineApple, 0, 0);
  }

  public static ResourceChanges sheep(int sheep) {
    return ResourceChanges.of(0, 0, 0, sheep, 0);
  }

  public static ResourceChanges wood(int wood) {
    return ResourceChanges.of(wood, 0, 0, 0, 0);
  }

  public static ResourceChanges allOf(ResourceCollection inventory) {
    return ResourceChanges.of(
        inventory.wood().value(),
        inventory.gold().value(),
        inventory.pineApple().value(),
        inventory.sheep().value(),
        inventory.sword().value());
  }

  public ResourceChanges add(ResourceChanges other) {
    return new ResourceChanges(
        wood + other.wood(),
        gold + other.gold(),
        pineApple + other.pineApple(),
        sheep + other.sheep(),
        sword + other.sword());
  }

  public ResourceChanges withSword(int newSwords) {
    return ResourceChanges.of(wood, gold, pineApple, sheep, newSwords);
  }

  public ResourceChanges withGold(int newGold) {
    return ResourceChanges.of(wood, newGold, pineApple, sheep, sword);
  }

  public ResourceChanges withSheep(int newSheep) {
    return ResourceChanges.of(wood, gold, pineApple, newSheep, sword);
  }

  public ResourceChanges withPineApple(int newPineApple) {
    return ResourceChanges.of(wood, gold, newPineApple, sheep, sword);
  }

  public ResourceChanges withWood(int newWood) {
    return ResourceChanges.of(newWood, gold, pineApple, sheep, sword);
  }

  public Wood asWood() {
    return new Wood(wood);
  }

  public Gold asGold() {
    return new Gold(gold);
  }

  public PineApple asPineApple() {
    return new PineApple(pineApple);
  }

  public Sheep asSheep() {
    return new Sheep(sheep);
  }

  public Sword asSword() {
    return new Sword(sword);
  }

  public ResourceChanges additions() {
    return new ResourceChanges(
        positive(wood), positive(gold), positive(pineApple), positive(sheep), positive(sword));
  }

  public ResourceChanges subtractions() {
    return new ResourceChanges(
        negativeAbs(wood),
        negativeAbs(gold),
        negativeAbs(pineApple),
        negativeAbs(sheep),
        negativeAbs(sword));
  }

  private static int positive(Integer value) {
    return Math.max(0, value);
  }

  private static int negativeAbs(Integer value) {
    return Math.abs(Math.min(0, value));
  }
}
