package dev.bstk.jstatuscli.tools.codex;

import dev.bstk.jstatuscli.tools.helper.JwtHelper;
import dev.bstk.jstatuscli.tools.helper.NpmPackageHelper;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static dev.bstk.jstatuscli.tools.helper.JsonHelper.getText;
import static dev.bstk.jstatuscli.tools.helper.JsonHelper.readJson;

public final class CodexRender {

  private static final String NOT_AVAILABLE = "Not available";
  private static final Path CODEX_HOME = Path.of(System.getProperty("user.home"), ".codex");
  private static final Path VERSION_FILE = CODEX_HOME.resolve("version.json");
  private static final Path AUTH_FILE = CODEX_HOME.resolve("auth.json");
  private static final Path CONFIG_FILE = CODEX_HOME.resolve("config.toml");
  private static final Pattern MODEL_PATTERN = Pattern.compile("^model\\s*=\\s*\"([^\"]+)\"$", Pattern.MULTILINE);

  public void render(final Terminal terminal) {
    final var codexInfo = loadCodexInfo();
    final var asb = new AttributedStringBuilder();

    asb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold())
        .append("Codex")
        .append("\n");

    appendField(asb, "Version", codexInfo.version());
    appendField(asb, "Model", codexInfo.model());
    appendField(asb, "Account", codexInfo.account());

    terminal.writer().println(asb.toAnsi());
    terminal.flush();
  }

  private static CodexInfo loadCodexInfo() {
    final var model = readModel();
    final var version = readVersion();
    final var account = readAccount();

    return new CodexInfo(version, model, account);
  }

  private static void appendField(final AttributedStringBuilder asb,
                                  final String label,
                                  final String value) {
    asb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold())
        .append(label)
        .append(": ")
        .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
        .append(value)
        .append("\n");
  }

  private static String readVersion() {
    return readJson(NpmPackageHelper.getNpmPackageFile())
        .flatMap(json -> Optional.ofNullable(getText(json, "version")))
        .or(() -> readJson(VERSION_FILE).flatMap(json -> Optional.ofNullable(getText(json, "latest_version"))))
        .orElse(NOT_AVAILABLE);
  }

  private static String readModel() {
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

  private static String readAccount() {
    return readJson(AUTH_FILE)
        .map(auth -> auth.path("tokens"))
        .flatMap(tokens -> {

          final var idToken = getText(tokens, "id_token");
          final var accessToken = getText(tokens, "access_token");
          final var payload = JwtHelper.decodePayload(idToken).or(() -> JwtHelper.decodePayload(accessToken));

          final var profile = payload
              .map(jsonNode -> jsonNode.path("https://api.openai.com/profile"))
              .orElse(null);

          final var name = firstNonBlank(
              getText(payload.orElse(null), "name"),
              getText(profile, "name"));

          final var email = firstNonBlank(
              getText(payload.orElse(null), "email"),
              getText(profile, "email"));

          final var accountId = getText(tokens, "account_id");
          final var formatAccount = formatAccount(name, email, accountId);

          return Optional.of(formatAccount);
        })
        .orElse(NOT_AVAILABLE);
  }

  private static String formatAccount(final String name,
                                      final String email,
                                      final String accountId) {
    return Optional.ofNullable(name)
        .filter(n -> Objects.nonNull(email))
        .map(n -> "%s <%s>".formatted(n, email))
        .or(() -> Optional.ofNullable(email))
        .or(() -> Optional.ofNullable(name))
        .or(() -> Optional.ofNullable(accountId))
        .orElse("Not logged in");
  }

  private static String firstNonBlank(final String first, final String second) {
    return Optional.ofNullable(first)
        .filter(s -> !s.isBlank())
        .or(() -> Optional.ofNullable(second).filter(s -> !s.isBlank()))
        .orElse(null);
  }
}
