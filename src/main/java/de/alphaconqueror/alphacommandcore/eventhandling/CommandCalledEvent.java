/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.eventhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.ICommandResult;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import de.alphaconqueror.alphaeventcore.eventhandling.Event;

public class CommandCalledEvent extends Event {

    private final ICommandSender sender;
    private final String[] args;
    private final ICommandResult commandResult;

    /**
     * Constructor of {@link CommandCalledEvent}.
     *
     * @param sender        The sender of the command.
     * @param args          The arguments given by the sender.
     * @param commandResult The command result after handling the command.
     */
    public CommandCalledEvent(ICommandSender sender, String[] args, ICommandResult commandResult) {
        this.sender = sender;
        this.args = args;
        this.commandResult = commandResult;
    }

    /**
     * Gets the {@link ICommandSender} of the command.
     *
     * @return The sender of the command.
     */
    public ICommandSender getSender() {
        return sender;
    }

    /**
     * Gets the arguments given by the sender.
     *
     * @return The arguments given by the sender.
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Gets the {@link ICommandResult} of the command.
     *
     * @return The command result of the command.
     */
    public ICommandResult getCommandResult() {
        return commandResult;
    }
}
