/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandHandler;

public class AlphaCommandCore {

    private static final CommandHandler commandHandler = new CommandHandler();

    /**
     * Used to get the {@link CommandHandler}.
     *
     * @return The command handler.
     */
    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
