/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.permission.PermissionRequired;
import de.alphaconqueror.alphacommandcore.eventhandling.CommandCalledEvent;
import de.alphaconqueror.alphacommandcore.eventhandling.CommandRegisteredEvent;
import de.alphaconqueror.alphaeventcore.AlphaEventCore;

import java.lang.reflect.Method;
import java.util.*;

public class CommandHandler {

    private final List<Command> commands = new ArrayList<>();

    /**
     * Handles a {@link Command}.
     *
     * @param command The command to be handled.
     * @param args    The arguments of the command.
     * @param sender  The {@link ICommandSender} of the command.
     *
     * @return The {@link ICommandResult} of the handled command.
     */
    public ICommandResult handleCommand(Command command, String[] args, ICommandSender sender) {
        ICommandResult commandResult = null;
        String permission = "";

        if(command == null) {
            commandResult = new ICommandResult.ERROR_COMMAND_NOT_FOUND(args[0]);
        } else {
            for(Method method: command.getClass().getMethods()) {
                if(method.getName().equals("handle")) {
                    if(method.getAnnotation(PermissionRequired.class) != null)
                        permission = method.getAnnotation(PermissionRequired.class).permission();
                    break;
                }
            }
        }

        if (!permission.isEmpty() && !sender.hasPermission(permission))
                commandResult = new ICommandResult.ERROR_PERMISSION(permission);

        if(commandResult == null)
            commandResult = command.handle(sender, args);

        AlphaEventCore.callEvent(new CommandCalledEvent(sender, args, commandResult));

        return commandResult;
    }

    /**
     * Handles a {@link Command} by a given message.
     *
     * @param message The message to be parsed and handled.
     * @param sender The {@link ICommandSender} of the message.
     *
     * @return The {@link ICommandResult} of the handled command.
     */
    public ICommandResult handle(String message, ICommandSender sender) {
        String[] arguments = null;
        Command command = null;
        boolean startsWithCallSymbol = false;

        for(Command cmd : commands) {
            if(message.startsWith(cmd.getCallSymbol())) {
                startsWithCallSymbol = true;

                List<String> args = splitMessage(message.substring(cmd.getCallSymbol().length()));

                if(invokesEqual(cmd.getInvokes(), args)) {
                    command = cmd;
                    arguments = args.toArray(new String[0]);
                    break;
                }
            }
        }

        if(!startsWithCallSymbol)
            return new ICommandResult.ERROR_NO_COMMAND(message.charAt(0));

        return handleCommand(command, arguments, sender);
    }

    /**
     * Handles a {@link Command} by given arguments.
     * Note: Does not check if the call symbol of the command is present.
     *
     * @param args   The arguments to be handled.
     * @param sender The {@link ICommandSender} of the arguments.
     *
     * @return The {@link ICommandResult} of the handled command.
     */
    public ICommandResult handle(String[] args, ICommandSender sender) {
        Command command = null;

        for(Command cmd : commands) {
            if(invokesEqual(cmd.getInvokes(), Arrays.asList(args))) {
                command =  cmd;
                break;
            }
        }

        return handleCommand(command, args, sender);
    }

    /**
     * Splits the message into an array without spaces.
     * Note: The first element contains all spaces in front for invoke matching purposes.
     *
     * @param message The message to be split.
     *
     * @return The split message.
     */
    private List<String> splitMessage(String message) {
        List<String> split = new ArrayList<>();
        String arg = "";

        for(int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if(c == ' ') {
                if(!isBlank(arg)) {
                    split.add(arg);
                    arg = "";
                } else if(split.size() == 0)
                    arg += c;
            } else
                arg += c;
        }

        if(!isBlank(arg))
            split.add(arg);

        return split;
    }

    /**
     * Checks, if the given string is blank.
     *
     * @param s The string to be checked.
     *
     * @return True, if the string is empty or only contains spaces, false, if otherwise.
     */
    private boolean isBlank(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != ' ')
                return false;
        }

        return true;
    }

    /**
     * Checks, if the given invokes are the same.
     *
     * @param invokes The invokes of the command.
     * @param args The arguments of the parsed message.
     *
     * @return True, if the invokes are equal, false, if otherwise.
     */
    private boolean invokesEqual(String[] invokes, List<String> args) {
        if(invokes.length > args.size())
            return false;

        for(int i = 0; i < invokes.length; i++) {
            if(!invokes[i].equals(args.get(i)))
                return false;
        }

        return true;
    }

    /**
     * Gets a {@link List} of {@link Command}s related to their invokes.
     *
     * @return A list containing commands related to their invokes.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Registers a {@link Command}.
     *
     * @param command The command to be added.
     */
    public void registerCommand(Command command) {
        commands.add(command);

        AlphaEventCore.callEvent(new CommandRegisteredEvent(command));
    }
}
