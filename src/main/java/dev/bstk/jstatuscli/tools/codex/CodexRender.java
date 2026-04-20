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
    asb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold())
        .append(dashboard());

    terminal.writer().print("\033[H");
    terminal.writer().println(asb.toAnsi());
    terminal.flush();
  }

  private String dashboard() {
    return """
        ╭─────────────────────────────────────────────────────────────────╮
        │  >_ OpenAI Codex %-46s │
        │                                                                 │
        │ Visit https://chatgpt.com/codex/settings/usage for up-to-date   │
        │ information on rate limits and credits                          │
        │                                                                 │
        │  Model:                %-40s │
        │  Agents.md:            %-40s │
        │  Account:              %-40s │
        ╰─────────────────────────────────────────────────────────────────╯
        """.formatted(
        version.getInfo(),
        model.getInfo(),
        "AGENTS.md",
        account.getInfo()
    );
  }
}
