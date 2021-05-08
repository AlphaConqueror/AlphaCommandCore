/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.eventhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandHandler;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import de.alphaconqueror.alphaeventcore.eventhandling.Event;

public class CommandCalledEvent extends Event {

  private final ICommandSender sender;
  private final CommandHandler commandHandler;
  private final String[] args;
  private final ICommandResult commandResult;

  /**
   * Constructor of {@link CommandCalledEvent}.
   *
   * @param sender         The sender of the command.
   * @param commandHandler The command handler used to handle the command.
   * @param args           The arguments given by the sender.
   * @param commandResult  The command result after handling the command.
   */
  public CommandCalledEvent(final ICommandSender sender, final CommandHandler commandHandler,
          final String[] args, final ICommandResult commandResult) {
    this.sender = sender;
    this.commandHandler = commandHandler;
    this.args = args.clone();
    this.commandResult = commandResult;
  }

  /**
   * Gets the {@link ICommandSender} of the command.
   *
   * @return The sender of the command.
   */
  public ICommandSender getSender() {
    return this.sender;
  }

  /**
   * Gets the {@link CommandHandler} used to handle the command.
   *
   * @return The command handler used.
   */
  public CommandHandler getCommandHandler() {
    return this.commandHandler;
  }

  /**
   * Gets the arguments given by the sender.
   *
   * @return The arguments given by the sender.
   */
  public String[] getArgs() {
    return this.args.clone();
  }

  /**
   * Gets the {@link ICommandResult} of the command.
   *
   * @return The command result of the command.
   */
  public ICommandResult getCommandResult() {
    return this.commandResult;
  }
}
