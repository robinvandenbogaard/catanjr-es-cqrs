package nl.robinthedev.catanjr.api.dto;

import java.util.List;
import nl.robinthedev.catanjr.game.model.board.ResourceType;
import nl.robinthedev.catanjr.game.model.resources.BuoyInventory;

public record BuoyDTO(ResourceTypeDTO resourceType) {
  public static List<BuoyDTO> of(BuoyInventory buoyInventory) {
    return buoyInventory.resources().stream().map(BuoyDTO::toNamed).map(BuoyDTO::new).toList();
  }

  private static ResourceTypeDTO toNamed(ResourceType resourceType) {
    return ResourceTypeDTO.valueOf(resourceType.name());
  }
}