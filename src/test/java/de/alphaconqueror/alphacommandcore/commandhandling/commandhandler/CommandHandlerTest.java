package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandHandler;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.testcommands.TestCommand1;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.testcommands.testcommandresults.TestResult1;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHandlerTest {

  private CommandHandler commandHandler;

  @BeforeEach
  void reset() {
    this.commandHandler = new CommandHandler(";");
  }

  @Test
  void simpleHandleTest() {
    assertEquals(this.commandHandler.handleCommand(Optional.of(new TestCommand1()),
            new String[] {""}, mock(
            ICommandSender.class)).getClass(), TestResult1.class);
  }
}
