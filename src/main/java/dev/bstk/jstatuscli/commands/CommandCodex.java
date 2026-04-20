package dev.bstk.jstatuscli.commands;

import dev.bstk.jstatuscli.JStatus;
import dev.bstk.jstatuscli.tools.codex.CodexRender;

public final class CommandCodex implements Command {

  private final CodexRender codexRender = new CodexRender();

  @Override
  public void execute() {
    codexRender.render(JStatus.getTerminal());
  }
}
