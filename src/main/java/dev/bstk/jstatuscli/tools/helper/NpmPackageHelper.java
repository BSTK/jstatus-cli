package dev.bstk.jstatuscli.tools.helper;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;

public final class NpmPackageHelper {

  private NpmPackageHelper() { }

  public static Path getNpmPackageFile() {
    final var appData = System.getenv("APPDATA");

    return StringUtils.isNotBlank(appData)
        ? Path.of(appData, "npm", "node_modules", "@openai", "codex", "package.json")
        : null;
  }
}
