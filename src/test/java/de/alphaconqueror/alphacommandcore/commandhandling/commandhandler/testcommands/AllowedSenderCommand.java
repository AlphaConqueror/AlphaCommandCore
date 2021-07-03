package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.testcommands;

import de.alphaconqueror.alphacommandcore.commandhandling.ICommand;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import de.alphaconqueror.alphacommandcore.commandhandling.permission.OnlyAllowedSenders;

public class AllowedSenderCommand implements ICommand {

  @Override
  @OnlyAllowedSenders(identifiers = {"testsender"})
  public ICommandResult handle(final ICommandSender sender, final String[] args) {
    return new ICommandResult.Okay();
  }

  @Override
  public String[] getInvokes() {
    return new String[]{"test"};
  }

  @Override
  public String[] getArguments() {
    return new String[0];
  }
}
