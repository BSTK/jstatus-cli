package dev.bstk.jstatuscli.commands;

import java.util.Objects;
import java.util.stream.Stream;

public enum Commands {

  CODEX("c"),
  HELP("h"),
  EXIT("e"),
  QUIT("q"),
  UNKNOWN(null);

  private final String alias;

  Commands(final String alias) {
    this.alias = alias;
  }

  public static Commands of(final String line) {
    if (Objects.isNull(line) || line.isBlank()) {
      return UNKNOWN;
    }

    return Stream.of(Commands.values())
        .filter(command -> isCommand(command, line))
        .findFirst()
        .orElse(UNKNOWN);
  }

  private static Boolean isCommand(final Commands command, final String line) {
    final var isCommand = command.name().equalsIgnoreCase(line);
    final var isCommandAlias = Objects.nonNull(command.alias) && command.alias.equalsIgnoreCase(line);

    return isCommand || isCommandAlias;
  }
}
