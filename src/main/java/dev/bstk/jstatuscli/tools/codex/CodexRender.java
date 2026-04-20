package dev.bstk.jstatuscli.tools.codex;

import dev.bstk.jstatuscli.tools.Info;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public final class CodexRender {

  private static final Info model = new CodexModel();
  private static final Info version = new CodexVersion();
  private static final Info account = new CodexAccount();

  public void render(final Terminal terminal) {
    final var asb = new AttributedStringBuilder();
    final var codexInfo = new CodexInfo(version.getInfo(), model.getInfo(), account.getInfo());

    asb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold())
        .append("Codex")
        .append("\n");

    appendField(asb, "Version", codexInfo.version());
    appendField(asb, "Model", codexInfo.model());
    appendField(asb, "Account", codexInfo.account());

    terminal.writer().println(asb.toAnsi());
    terminal.flush();
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
}
