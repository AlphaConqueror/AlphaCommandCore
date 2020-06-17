/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public class CommandContainer {

    private final ICommandSender sender;
    private final String invoke;
    private final String[] args;

    /**
     * Constructor of {@link CommandContainer}.
     *
     * @param sender The {@link ICommandSender} who sent the command.
     * @param invoke The first argument given by the sender.
     * @param args   A list of arguments given by the sender.
     */
    public CommandContainer(ICommandSender sender, String invoke, String[] args) {
        this.sender = sender;
        this.invoke = invoke;
        this.args = args;
    }

    /**
     * Gets the {@link ICommandSender} who sent the command.
     *
     * @return {@link ICommandSender} who sent the command.
     */
    public ICommandSender getSender() {
        return sender;
    }

    /**
     * Gets the invoke given by the sender.
     *
     * @return The invoke given by the sender.
     */
    public String getInvoke() {
        return invoke;
    }

    /**
     * Gets the list of arguments given by the sender.
     *
     * @return A list of arguments given by the sender.
     */
    public String[] getArgs() {
        return args;
    }
}
