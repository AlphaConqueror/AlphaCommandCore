/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.eventhandling;

public abstract class Event {
    private String name;

    /**
     * Used to get the name of the event class.
     *
     * @return The name of the event class.
     */
    public String getEventName() {
        if(name == null)
            name = getClass().getSimpleName();

        return name;
    }
}
