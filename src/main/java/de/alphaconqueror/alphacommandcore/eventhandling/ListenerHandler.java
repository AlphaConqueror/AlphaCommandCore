/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.eventhandling;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListenerHandler {

    private final List<Listener> listeners = new ArrayList<Listener>();

    /**
     * Used to call an {@link Event}.
     *
     * @param event The event to be called.
     */
    public void callEvent(Object event) {
        for(Listener listener : listeners)
            for(Method method : listener.getClass().getMethods())
                if(method.isAnnotationPresent(EventHandler.class)) {
                    try {
                        Class<?>[] parameterTypes = method.getParameterTypes();

                        if(parameterTypes.length == 1) {
                            Class<?> parameter = parameterTypes[0];

                            if(parameter.getCanonicalName().equalsIgnoreCase(event.getClass().getCanonicalName())) {
                                method.setAccessible(true);
                                method.invoke(listener, new Object[] {event});
                            }
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
    }

    /**
     * Used to get all listener classes.
     *
     * @return A list of all listener classes.
     */
    public List<Listener> getListeners() {
        return listeners;
    }

    /**
     * Used to add a listener class.
     *
     * @param listener The listener class.
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
}
