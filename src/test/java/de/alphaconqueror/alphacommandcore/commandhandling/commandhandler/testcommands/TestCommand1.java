package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.testcommands;

import de.alphaconqueror.alphacommandcore.commandhandling.ICommand;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.testcommands.testcommandresults.TestResult1;

public class TestCommand1 implements ICommand {

  public String[] receivedArgs;

  @Override
  public ICommandResult handle(final ICommandSender sender, final String[] args) {
    this.receivedArgs = args;
    return new TestResult1();
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
