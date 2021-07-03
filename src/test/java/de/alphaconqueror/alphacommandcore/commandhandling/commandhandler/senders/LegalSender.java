package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.senders;

import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;

public class LegalSender implements ICommandSender {
  @Override
  public String getIdentifier() {
    return "testsender";
  }

  @Override
  public boolean hasPermission(final String permission) {
    return true;
  }
}
