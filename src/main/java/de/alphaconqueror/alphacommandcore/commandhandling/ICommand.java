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
     * @return {@code CommandError.ERROR_OK} if command has been handled correctly,
     *         other command error specific to the reason if it has not.
     */
    CommandError handle(final ICommandSender sender, final String[] args);

    /**
     * Gets the invoke used to call the command.
     *
     * @return The invoke of the command.
     *
     * Example: In '/test', "test" would be the invoke.
     */
    String getInvoke();

    /**
     * Gets the syntax of the command.
     *
     * @return The syntax of the command.
     *
     * Example: /test <String: message> [String: suffix]
     * Example command-line syntax key:
     *   Notation   |  Description
     * -----------------------------------------------------------------------------
     * Text         |  Items you must type as shown.
     * <Text>       |  Placeholder for which you must supply a value.
     * [Text]       |  Optional items.
     * {a1,...,an}  |  Set of required items. You must choose one.
     * (|)          |  Separator for mutually exclusive items. You must choose one.
     * (...)        |  Items that can be repeated and used multiple times.
     *
     * Copied from:
     * https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/command-line-syntax-key
     */
    String getSyntax();
}
