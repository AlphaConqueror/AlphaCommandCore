/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public interface ICommandSender {

  /**
   * Verifies, if the command sender has a certain permission.
   *
   * @param permission The permission to be verified.
   *
   * @return True, if the command sender has the permission, false, if otherwise.
   */
  boolean hasPermission(String permission);
}
