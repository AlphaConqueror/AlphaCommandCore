package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandHandler;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.commandresults.TestResult1;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.testcommands.TestCommand1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimpleCommandHandlerTest {

  private CommandHandler commandHandler;
  private TestCommand1 testCommand1;

  @BeforeEach
  void init() {
    this.commandHandler = new CommandHandler("/");
    this.testCommand1 = new TestCommand1();
    this.commandHandler.registerCommand(this.testCommand1);
  }

  @Test
  void simpleNoCommandTest() throws NoSuchMethodException {
    assertEquals(ICommandResult.ErrorNoCommand.class,
            this.commandHandler.handle("", mock(ICommandSender.class)).getClass());
    assertEquals(ICommandResult.ErrorNoCommand.class,
            this.commandHandler.handle("test", mock(ICommandSender.class)).getClass());
  }

  @Test
  void simpleHandleTest() throws NoSuchMethodException {
    assertEquals(TestResult1.class,
            this.commandHandler.handle("/test", mock(ICommandSender.class)).getClass());
  }

  @Test
  void simpleArgumentsTest() throws NoSuchMethodException {
    final String args = "arg1 arg2 arg3";

    this.commandHandler.handle("/test " + args, mock(ICommandSender.class));
    assertArrayEquals(args.split(" "), this.testCommand1.receivedArgs);
  }
}
