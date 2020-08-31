/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public abstract class Command {

    /**
     * Handles a command.
     *
     * @param sender The command sender.
     * @param args   A list of all arguments given.
     *
     * @return An executable {@link ICommandResult}, if the command has been handled correctly,
     *         a not executable command result, if it has not.
     */
    public abstract ICommandResult handle(final ICommandSender sender, final String[] args);

    /**
     * Gets the symbol used to call the command.
     *
     * @return The symbol used to call the command.
     *
     * Example: In '/test', '/' would be the call symbol.
     *          In '//test', '//' would be the call symbol.
     */
    public abstract String getCallSymbol();

    /**
     * Gets the invokes used to call the command.
     *
     * @return The invoke of the command.
     *
     * Example: In '/test', "test" would be the invoke.
     *          In '/test status 1' or '/test status 2', the user can decide whether the invokes
     *          are 'test' or 'test status'.
     */
    public abstract String[] getInvokes();

    /**
     * Gets the arguments of the command.
     *
     * @return The arguments of the command.
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
    public abstract String[] getArguments();

    /**
     * Gets the syntax of the command consisting of the call symbol, the invokes and the arguments.
     *
     * @return The syntax of the command.
     */
    public String getSyntax() {
        StringBuilder syntax = new StringBuilder(getCallSymbol());
        String[] invokes = getInvokes(),
                 arguments = getArguments();

        for(int i = 0; i < invokes.length; i++) {
            syntax.append(invokes[i]);

            if(i + 1 < invokes.length)
                syntax.append(" ");
        }

        for(int i = 0; i < arguments.length; i++) {
            syntax.append(arguments[i]);

            if(i + 1 < arguments.length)
                syntax.append(" ");
        }

        return syntax.toString();
    }
}
