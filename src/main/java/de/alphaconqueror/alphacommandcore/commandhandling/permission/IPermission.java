/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling.permission;

public interface IPermission {

    /**
     * Used to get the identifier of the permission.
     * Every permission has a certain identifier.
     *
     * @return The identifier of the permission.
     */
    String getIdentifier();
}
