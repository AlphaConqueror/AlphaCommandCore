/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.eventhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandError;
import de.alphaconqueror.alphacommandcore.commandhandling.ICommandSender;
import de.alphaconqueror.alphaeventcore.eventhandling.Event;

public class CommandCalledEvent extends Event {

    private final ICommandSender sender;
    private final String[] args;
    private final CommandError commandError;

    /**
     * Constructor of {@link CommandCalledEvent}.
     *
     * @param sender       The sender of the command.
     * @param args         The arguments given by the sender.
     * @param commandError The command error after handling the command.
     */
    public CommandCalledEvent(ICommandSender sender, String[] args, CommandError commandError) {
        this.sender = sender;
        this.args = args;
        this.commandError = commandError;
    }

    /**
     * Used to get the {@link ICommandSender} of the command.
     *
     * @return The sender of the command.
     */
    public ICommandSender getSender() {
        return sender;
    }

    /**
     * Used to get the arguments given by the sender.
     *
     * @return The arguments given by the sender.
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Used to get the {@link CommandError} of the command.
     *
     * @return The command error of the command.
     */
    public CommandError getCommandError() {
        return commandError;
    }
}
