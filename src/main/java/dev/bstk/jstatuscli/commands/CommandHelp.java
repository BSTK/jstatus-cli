package dev.bstk.jstatuscli.commands;

import dev.bstk.jstatuscli.JStatus;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public final class CommandHelp implements Command {

  // @formatter:off
  private static final String[][] COMMANDS = {
      {"monitor", "Inicia o dashboard de monitoramento interativo."},
      {"config",  "Gerencia as chaves de API e preferências."},
      {"help",    "Mostra esta mensagem de ajuda."}
  };

  private static final String[][] HOTKEYS = {
      {"gemini",  "Alterna para Google Gemini."},
      {"codex",   "Alterna para OpenAI Codex."},
      {"q",       "Encerra a ferramenta."},
      {"e",       "Encerra a ferramenta."}
  };
  // @formatter:on

  @Override
  public void execute() {
    final var terminal = JStatus.getTerminal();
    final var asb = new AttributedStringBuilder();

    asb.style(AttributedStyle.DEFAULT.italic())
        .append("Monitor de uso e performance para agentes de IA.")
        .append("\n\n");

    appendSection(asb, "Commands:", COMMANDS);
    appendSection(asb, "Hotkeys (em execução):", HOTKEYS);

    asb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BRIGHT))
        .append("Reporte problemas em: ")
        .style(AttributedStyle.DEFAULT.underline())
        .append("https://github.com")
        .append("\n");

    terminal.writer().println(asb.toAnsi());
    terminal.flush();
  }

  private static void appendSection(final AttributedStringBuilder asb,
                                    final String title,
                                    final String[][] items) {
    asb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold())
        .append(title)
        .append("\n");

    for (String[] item : items) {
      asb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN))
          .append("  ")
          .append(String.format("%-10s", item[0]))
          .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
          .append(item[1])
          .append("\n");
    }

    asb.append("\n");
  }
}
