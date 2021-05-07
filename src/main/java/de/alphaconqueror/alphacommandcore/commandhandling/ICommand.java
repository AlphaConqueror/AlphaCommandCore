/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public interface ICommand {

  /**
   * Handles a command.
   *
   * @param sender The command sender.
   * @param args   A list of all arguments given.
   *
   * @return An executable {@link ICommandResult}, if the command has been handled correctly,
   *         a not executable command result, if it has not.
   */
  ICommandResult handle(final ICommandSender sender, final String[] args);

  /**
   * Gets the invokes used to call the command.
   *
   * @return The invoke of the command.
   *
   *         <p>Example: In '/test', "test" would be the invoke.
   *         In '/test status 1' or '/test status 2', the user can decide whether the invokes
   *         are 'test' or 'test status'.
   */
  String[] getInvokes();

  /**
   * Gets the expected arguments of the command.
   *
   * @return The expected arguments of the command.
   *         <p>
   *         Example: /test &ltString: message&gt [String: suffix]
   *         Example command-line syntax key:
   *         Notation     |  Description
   *         -----------------------------------------------------------------------------
   *         Text         |  Items you must type as shown.
   *         &ltText&gt   |  Placeholder for which you must supply a value.
   *         [Text]       |  Optional items.
   *         {a1,...,an}  |  Set of required items. You must choose one.
   *         (|)          |  Separator for mutually exclusive items. You must choose one.
   *         (...)        |  Items that can be repeated and used multiple times.
   *         <p>
   *         Source:
   *         https://docs.microsoft.com/en-us/windows-server/administration/windows-commands
   *         /command-line-syntax-key
   */
  String[] getArguments();
}
