/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.permission.IPermission;

public interface ICommandSender {

    /**
     * Sends a message to the command sender.
     *
     * @param message The message to be sent.
     */
    void sendMessage(String message);

    /**
     * Adds a permission to the command sender.
     *
     * @param permission The permission to be added.
     */
    ICommandSender addPermission(IPermission permission);

    /**
     * Verifies, if the command sender has a certain permission.
     *
     * @param permission The permission to be verified.
     *
     * @return True, if the command sender has the permission, false, if otherwise.
     */
    boolean hasPermission(String permission);
}
