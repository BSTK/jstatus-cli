package dev.bstk.jstatuscli.tools.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class JsonHelper {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }

  public static String getText(final JsonNode node, final String field) {
    if (Objects.isNull(node)
        || node.isMissingNode()
        || node.path(field).isMissingNode()
        || node.path(field).isNull()) {
      return null;
    }

    final var value = node.path(field).asText();
    return StringUtils.isBlank(value) ? StringUtils.EMPTY : value;
  }

  public static Optional<JsonNode> readJson(final Path file) {
    if (file == null || !Files.exists(file)) {
      return Optional.empty();
    }

    try {
      return Optional.of(JsonHelper.getObjectMapper().readTree(file.toFile()));
    } catch (IOException e) {
      return Optional.empty();
    }
  }
}
