/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public interface ICommandSender {

    /**
     * Used to get the name of the command sender.
     *
     * @return The name of the command sender.
     */
    String getName();

    /**
     * Used to add a permission to the command sender.
     *
     * @param permission The permission to be added.
     */
    ICommandSender addPermission(IPermission permission);

    /**
     * Used to verify if the command sender has a certain permission.
     *
     * @param permission The permission to be verified.
     *
     * @return True, if the command sender has the permission, false, if otherwise.
     */
    boolean hasPermission(String permission);
}
