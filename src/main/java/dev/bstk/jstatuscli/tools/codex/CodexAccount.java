package dev.bstk.jstatuscli.tools.codex;

import dev.bstk.jstatuscli.tools.Info;
import dev.bstk.jstatuscli.tools.helper.JwtHelper;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static dev.bstk.jstatuscli.tools.codex.CodexConstants.CODEX_HOME;
import static dev.bstk.jstatuscli.tools.codex.CodexConstants.NOT_AVAILABLE;
import static dev.bstk.jstatuscli.tools.helper.JsonHelper.getText;
import static dev.bstk.jstatuscli.tools.helper.JsonHelper.readJson;

public class CodexAccount implements Info {

  private static final Path AUTH_FILE = CODEX_HOME.resolve("auth.json");

  @Override
  public String getInfo() {
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
