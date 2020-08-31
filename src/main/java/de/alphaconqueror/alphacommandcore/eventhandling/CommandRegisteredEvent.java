/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.eventhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.Command;
import de.alphaconqueror.alphaeventcore.eventhandling.Event;

public class CommandRegisteredEvent extends Event {

    private final Command command;

    public CommandRegisteredEvent(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
