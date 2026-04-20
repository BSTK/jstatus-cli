package dev.bstk.jstatuscli.commands;

import dev.bstk.jstatuscli.JStatus;

public final class CommandUnknow implements Command {

  @Override
  public void execute() {
    final var terminal = JStatus.getTerminal();
    terminal.writer().println("Uknown command!");
    terminal.flush();
  }
}
