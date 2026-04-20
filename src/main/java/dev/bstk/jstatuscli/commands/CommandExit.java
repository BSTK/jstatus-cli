package dev.bstk.jstatuscli.commands;

import dev.bstk.jstatuscli.JStatus;

import java.io.IOException;

public final class CommandExit implements Command {

  @Override
  public void execute() {
    try {
      final var terminal = JStatus.getTerminal();
      terminal.writer().println("Goodbye!");
      terminal.close();

      System.exit(0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
