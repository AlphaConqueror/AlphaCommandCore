/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.permission.PermissionRequired;
import de.alphaconqueror.alphacommandcore.eventhandling.CommandCalledEvent;
import de.alphaconqueror.alphaeventcore.AlphaEventCore;

import java.lang.reflect.Method;
import java.util.*;

public class CommandHandler {

    private final Map<String, ICommand> commands = new HashMap<>();

    /**
     * Used to handle a command.
     *
     * @param message The unparsed message containing invoke and arguments.
     * @param sender  The sender of the message.
     */
    public CommandError handleCommand(String message, ICommandSender sender) {
        CommandError commandError = null;
        String permission = "";

        final CommandContainer commandContainer = parse(message, sender);
        final ICommand cmd = commands.get(commandContainer.getInvoke());

        for(Method method : cmd.getClass().getMethods()) {
            if (method.getName().equals("handle")) {
                if(method.getAnnotation(PermissionRequired.class) != null)
                    permission = method.getAnnotation(PermissionRequired.class).permission();
                break;
            }
        }

        if (!permission.equals("")) {
            if(!commandContainer.getSender().hasPermission(permission))
                commandError =  CommandError.ERROR_PERMISSION;
        }

        if(commandError == null) {
            if(commands.containsKey(commandContainer.getInvoke())) {
                ICommand command = commands.get(commandContainer.getInvoke());

                commandError = command.handle(commandContainer.getSender(), commandContainer.getArgs());
            } else
                commandError = CommandError.ERROR_COMMAND_NOT_FOUND;
        }

        AlphaEventCore.callEvent(new CommandCalledEvent(commandContainer.getSender(), commandContainer.getArgs(), commandError));

        return commandError;
    }

    /**
     * Used to parse a message into a {@link CommandContainer}.
     *
     * @param message The unparsed message.
     * @param sender  The sender of the message.
     *
     * @return The command container containing all information about the command.
     */
    private CommandContainer parse(String message, ICommandSender sender) {
        String[] split = message.split(" "),
                args = new String[split.length - 1];
        List<String> splitList = new LinkedList<>();

        Collections.addAll(splitList, split);

        splitList.subList(1, splitList.size()).toArray(args);

        return new CommandContainer(sender, split[0], args);
    }

    /**
     * Used to get a {@link Map} of {@link ICommand}s related to their invokes.
     *
     * @return A map containing commands related to their invokes.
     */
    public Map<String, ICommand> getCommands() {
        return commands;
    }

    /**
     * Used to register commands.
     *
     * @param invoke  The keyword the command is related to.
     * @param command The command to be added.
     */
    public void registerCommand(String invoke, ICommand command) {
        commands.put(invoke, command);
    }
}
