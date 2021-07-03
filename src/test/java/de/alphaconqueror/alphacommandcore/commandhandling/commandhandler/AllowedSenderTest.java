package de.alphaconqueror.alphacommandcore.commandhandling.commandhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandHandler;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.senders.IllegalSender;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.senders.LegalSender;
import de.alphaconqueror.alphacommandcore.commandhandling.commandhandler.testcommands.AllowedSenderCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AllowedSenderTest {

  private CommandHandler commandHandler;

  @BeforeEach
  void init() {
    this.commandHandler = new CommandHandler("/");
    final AllowedSenderCommand allowedSenderCommand = new AllowedSenderCommand();
    this.commandHandler.registerCommand(allowedSenderCommand);
  }

  @Test
  void simpleNullSender() {
    final ICommandResult commandResult = this.commandHandler.handle("/test", null);

    assertEquals(ICommandResult.ErrorIllegalSender.class, commandResult.getClass());
    assertNull(((ICommandResult.ErrorIllegalSender) commandResult).getCommandSenderClass());
  }

  @Test
  void simpleLegalSender() {
    final ICommandResult commandResult = this.commandHandler.handle("/test", new LegalSender());

    assertEquals(ICommandResult.Okay.class, commandResult.getClass());
  }

  @Test
  void simpleIllegalSender() {
    final ICommandResult commandResult = this.commandHandler.handle("/test", new IllegalSender());

    assertEquals(ICommandResult.ErrorIllegalSender.class, commandResult.getClass());
    assertEquals(IllegalSender.class,
            ((ICommandResult.ErrorIllegalSender) commandResult).getCommandSenderClass());
  }
}
