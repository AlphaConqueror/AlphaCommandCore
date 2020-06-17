/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public interface ICommand {

    /**
     * Used to handle command.
     *
     * @param sender The command sender.
     * @param invoke The command invoke.
     * @param args   A list of all arguments given.
     *
     * @return {@code CommandError.ERROR_OK} if command has been handled correctly,
     *         other command error specific to the reason if it has not.
     */
    CommandError handle(final ICommandSender sender, final String invoke, final String[] args);
}
