package dev.bstk.jstatuscli.tools.helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class JwtHelper {

  public static Optional<JsonNode> decodePayload(final String jwt) {
    if (StringUtils.isBlank(jwt)) {
      return Optional.empty();
    }

    final var parts = jwt.split("\\.");
    if (parts.length < 2) {
      return Optional.empty();
    }

    try {
      final var payloadBytes = Base64.getUrlDecoder().decode(parts[1]);
      return Optional.of(JsonHelper.getObjectMapper().readTree(payloadBytes));
    } catch (IllegalArgumentException | IOException e) {
      return Optional.empty();
    }
  }
}
