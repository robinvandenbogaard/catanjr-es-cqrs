package nl.robinthedev.catanjr.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.catanjr.game.model.board.ResourceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ResourceTypeDTOTest {

  @ParameterizedTest
  @EnumSource(ResourceType.class)
  void can_map_all_resource_types(ResourceType resourceType) {
    assertThat(ResourceTypeDTO.valueOf(resourceType.name())).isNotNull();
  }
}
