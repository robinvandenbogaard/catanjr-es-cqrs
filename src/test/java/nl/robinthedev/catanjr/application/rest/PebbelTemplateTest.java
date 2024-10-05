package nl.robinthedev.catanjr.application.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

class PebbelTemplateTest {

  @Test
  void renders_properties_from_template_file() {
    assertThat(PebbleTemplate.processTemplate("pebbleTemplateTest", Map.of("target", new ExampleData("myValue")))).isEqualTo("<h1>myValue</h1>");
  }

  record ExampleData(String property) {
  }
}