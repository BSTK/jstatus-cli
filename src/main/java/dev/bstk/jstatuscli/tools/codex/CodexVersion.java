package dev.bstk.jstatuscli.tools.codex;

import dev.bstk.jstatuscli.tools.Info;
import dev.bstk.jstatuscli.tools.helper.NpmPackageHelper;

import java.nio.file.Path;
import java.util.Optional;

import static dev.bstk.jstatuscli.tools.codex.CodexConstants.CODEX_HOME;
import static dev.bstk.jstatuscli.tools.codex.CodexConstants.NOT_AVAILABLE;
import static dev.bstk.jstatuscli.tools.helper.JsonHelper.getText;
import static dev.bstk.jstatuscli.tools.helper.JsonHelper.readJson;

public class CodexVersion implements Info {

  private static final Path VERSION_FILE = CODEX_HOME.resolve("version.json");

  @Override
  public String getInfo() {
    return readJson(NpmPackageHelper.getNpmPackageFile())
        .flatMap(json -> Optional.ofNullable(getText(json, "version")))
        .or(() -> readJson(VERSION_FILE).flatMap(json -> Optional.ofNullable(getText(json, "latest_version"))))
        .orElse(NOT_AVAILABLE);
  }
}
