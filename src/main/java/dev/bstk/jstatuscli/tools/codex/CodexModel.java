package dev.bstk.jstatuscli.tools.codex;

import dev.bstk.jstatuscli.tools.Info;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static dev.bstk.jstatuscli.tools.codex.CodexConstants.CODEX_HOME;
import static dev.bstk.jstatuscli.tools.codex.CodexConstants.NOT_AVAILABLE;

public class CodexModel implements Info {

  private static final Path CONFIG_FILE = CODEX_HOME.resolve("config.toml");
  private static final Pattern MODEL_PATTERN = Pattern.compile("^model\\s*=\\s*\"([^\"]+)\"$", Pattern.MULTILINE);

  @Override
  public String getInfo() {
    if (!Files.exists(CONFIG_FILE)) {
      return NOT_AVAILABLE;
    }

    try {
      final var config = Files.readString(CONFIG_FILE, StandardCharsets.UTF_8);
      final var matcher = MODEL_PATTERN.matcher(config);

      return matcher.find() ? matcher.group(1) : NOT_AVAILABLE;
    } catch (IOException e) {
      return NOT_AVAILABLE;
    }
  }
}
