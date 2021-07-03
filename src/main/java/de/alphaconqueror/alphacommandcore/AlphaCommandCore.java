/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandHandler;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlphaCommandCore {

  /**
   * The list of {@link CommandHandler}s reverse sorted after the length of the call symbol.
   */
  private static final List<CommandHandler> COMMAND_HANDLERS = new ArrayList<>();

  /**
   * Parses a given string and its {@link ICommandSender} to the {@link CommandHandler}s
   * to handle it.
   * Note: Only handles the first command that matches the call symbol and the invokes.
   *
   * @param message The message to be parsed.
   * @param sender  The sender of the message.
   */
  public static void parse(final String message, final ICommandSender sender) {
    for (final CommandHandler commandHandler : COMMAND_HANDLERS) {
      if (!(commandHandler.handle(message, sender) instanceof ICommandResult.ErrorNoCommand)) {
        break;
      }
    }
  }

  /**
   * Adds a {@link CommandHandler} to the collection of command handlers
   * to be respected in the parsing process.
   * Note: The command handlers can not have the same call symbol.
   *
   * @param commandHandler The command handler to be added.
   * @throws UnsupportedOperationException if the command handler has the same call symbol
   *                                       as an existing command handler.
   */
  public static void addCommandHandler(final CommandHandler commandHandler) {
    if (COMMAND_HANDLERS.stream().map(CommandHandler::getCallSymbol)
            .anyMatch(commandHandler.getCallSymbol()::equals)) {
      throw new UnsupportedOperationException("Duplicate call symbol.");
    }

    COMMAND_HANDLERS.add(commandHandler);
    COMMAND_HANDLERS
            .sort(Comparator.comparingInt(cmdHandler -> cmdHandler.getCallSymbol().length()));
    Collections.reverse(COMMAND_HANDLERS);
  }

  /**
   * Gets the {@link CommandHandler}s.
   *
   * @return A copy of the command handlers.
   */
  public static List<CommandHandler> getCommandHandlers() {
    return List.copyOf(COMMAND_HANDLERS);
  }
}
