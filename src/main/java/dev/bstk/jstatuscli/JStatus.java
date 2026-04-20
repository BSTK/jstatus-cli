package dev.bstk.jstatuscli;

import dev.bstk.jstatuscli.commands.Command;
import dev.bstk.jstatuscli.commands.CommandCodex;
import dev.bstk.jstatuscli.commands.CommandExit;
import dev.bstk.jstatuscli.commands.CommandHelp;
import dev.bstk.jstatuscli.commands.CommandUnknow;
import dev.bstk.jstatuscli.commands.Commands;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public final class JStatus {

  private static final String J_STATUS = "JStatus";

  private static final Map<Commands, Command> COMMANDS = Map.of(
      Commands.CODEX, new CommandCodex(),
      Commands.HELP, new CommandHelp(),
      Commands.EXIT, new CommandExit(),
      Commands.QUIT, new CommandExit()
  );

  private JStatus() { }

  public static void init() {
    final var terminal = getTerminal();
    final var reader = LineReaderBuilder.builder()
        .terminal(terminal)
        .appName(J_STATUS)
        .build();

    while (true) {
      final var line = reader.readLine("jstatus > ").trim();
      final var command = COMMANDS.getOrDefault(Commands.of(line), new CommandUnknow());
      if (Objects.isNull(command)) {
        break;
      }

      command.execute();
    }
  }

  public static Terminal getTerminal() {
    return TerminalHolder.INSTANCE;
  }

  private static class TerminalHolder {
    private static final Terminal INSTANCE;

    static {
      try {
        INSTANCE = TerminalBuilder.builder()
            .name(J_STATUS)
            .system(true)
            .encoding(StandardCharsets.UTF_8)
            .build();

      } catch (IOException e) {
        throw new RuntimeException("Falha ao inicializar o Terminal", e);
      }
    }
  }
}
