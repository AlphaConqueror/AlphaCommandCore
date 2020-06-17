/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore;

import de.alphaconqueror.alphacommandcore.commandhandling.CommandHandler;
import de.alphaconqueror.alphacommandcore.eventhandling.ListenerHandler;

public class AlphaCommandCore {

    private final static CommandHandler commandHandler = new CommandHandler();
    private final static ListenerHandler listenerHandler = new ListenerHandler();

    /**
     * Used to get the {@link CommandHandler}.
     *
     * @return The command handler.
     */
    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * Used to get the {@link ListenerHandler}.
     *
     * @return The listener handler.
     */
    public static ListenerHandler getListenerHandler() {
        return listenerHandler;
    }
}
