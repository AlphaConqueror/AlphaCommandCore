package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.commandresults;

import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;

public class TestResult1 implements ICommandResult {

  @Override
  public boolean isExecutable() {
    return true;
  }
}
