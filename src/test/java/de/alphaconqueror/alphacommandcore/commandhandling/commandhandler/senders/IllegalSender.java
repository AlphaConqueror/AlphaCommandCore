package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.senders;

import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;

public class IllegalSender implements ICommandSender {
  
  @Override
  public String getIdentifier() {
    return "";
  }

  @Override
  public boolean hasPermission(final String permission) {
    return true;
  }
}
