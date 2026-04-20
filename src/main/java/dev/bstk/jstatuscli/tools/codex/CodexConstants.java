package dev.bstk.jstatuscli.tools.codex;

import java.nio.file.Path;

public class CodexConstants {
  public static final String NOT_AVAILABLE = "Not available";
  public static final Path CODEX_HOME = Path.of(System.getProperty("user.home"), ".codex");

  private CodexConstants() {}
}
