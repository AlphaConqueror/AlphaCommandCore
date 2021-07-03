/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.annotations.OnlyAllowedSenders;

public interface ICommandSender {

  /**
   * Identifier used for
   * {@link OnlyAllowedSenders}.
   *
   * @return The identifier used to check if this sender class is allowed to execute a certain
   * {@link ICommand}.
   */
  String getIdentifier();

  /**
   * Verifies, if the command sender has a certain permission.
   *
   * @param permission The permission to be verified.
   * @return True, if the command sender has the permission, false, if otherwise.
   */
  boolean hasPermission(String permission);
}
