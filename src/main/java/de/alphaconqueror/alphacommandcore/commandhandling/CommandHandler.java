/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

import de.alphaconqueror.alphacommandcore.commandhandling.permission.OnlyAllowedSenders;
import de.alphaconqueror.alphacommandcore.commandhandling.permission.PermissionRequired;
import de.alphaconqueror.alphacommandcore.eventhandling.CommandCalledEvent;
import de.alphaconqueror.alphaeventcore.AlphaEventCore;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("PMD.LinguisticNaming")
public class CommandHandler {

  private final List<ICommand> commands = new ArrayList<>();
  private String callSymbol = "";
  private final List<String> defaultInvokes = new ArrayList<>();
  private String separator = " ";
  private boolean ignoreBlanks = true;

  public CommandHandler() { }

  public CommandHandler(final String callSymbol) {
    this.callSymbol = callSymbol;
  }

  public CommandHandler addDefaultInvokes(final String... defaultInvokes) {
    addDefaultInvokes(Arrays.asList(defaultInvokes));
    return this;
  }

  public CommandHandler addDefaultInvokes(final Collection<String> defaultInvokes) {
    this.defaultInvokes.addAll(defaultInvokes);
    return this;
  }

  public CommandHandler setSeparator(final String separator) {
    this.separator = separator;
    return this;
  }

  public CommandHandler setIgnoreBlanks(final boolean ignoreBlanks) {
    this.ignoreBlanks = ignoreBlanks;
    return this;
  }

  /**
   * Handles a {@link ICommand} by a given message.
   *
   * @param message The message to be parsed and handled.
   * @param sender  The {@link ICommandSender} of the message.
   * @return The {@link ICommandResult} of the handled command.
   */
  public ICommandResult handle(final String message, final ICommandSender sender) {
    if (!message.startsWith(this.callSymbol)) {
      final ICommandResult commandResult = new ICommandResult.ErrorNoCommand(this.callSymbol);

      AlphaEventCore.callEvent(new CommandCalledEvent(sender, this, new String[0], commandResult));

      return commandResult;
    }

    return handle(splitMessage(message.substring(this.callSymbol.length())).toArray(new String[0]),
            sender);
  }

  /**
   * Handles a {@link ICommand} by given arguments.
   * Note: Does not check if the call symbol of the command is present.
   *
   * @param args   The arguments to be handled.
   * @param sender The {@link ICommandSender} of the arguments.
   * @return The {@link ICommandResult} of the handled command.
   */
  public ICommandResult handle(final String[] args, final ICommandSender sender) {
    Optional<ICommandResult> commandResult = Optional.empty();
    List<String> argList = List.of(args);

    if (!invokesMatch(this.defaultInvokes.toArray(new String[0]), argList.toArray(new String[0]))) {
      commandResult = Optional.of(new ICommandResult.ErrorNoInvokeMatch(
              this.defaultInvokes.toArray(new String[0])));
    }

    if (commandResult.isEmpty()) {
      argList = argList.subList(this.defaultInvokes.size(), argList.size());

      final Optional<ICommand> command = getCommandMatchingInvokes(argList);

      if (command.isPresent()) {
        commandResult = Optional.ofNullable(handleCommand(command.get(),
                (argList = argList.subList(command.get().getInvokes().length, argList.size()))
                        .toArray(new String[0]), sender));
      } else {
        commandResult = Optional
                .of(new ICommandResult.ErrorCommandNotFound(argList.toArray(new String[0])));
      }
    }

    AlphaEventCore.callEvent(new CommandCalledEvent(sender, this, argList.toArray(new String[0]),
            commandResult.orElse(null)));

    return commandResult.orElse(null);
  }

  /**
   * Handles a {@link ICommand}.
   *
   * @param command The command to be handled.
   * @param args    The arguments of the command.
   * @param sender  The {@link ICommandSender} of the command.
   * @return The {@link ICommandResult} of the handled command.
   */
  public static ICommandResult handleCommand(final ICommand command, final String[] args,
          final ICommandSender sender) {
    Optional<ICommandResult> commandResult = Optional.empty();

    try {
      final Method method = command.getClass()
              .getMethod("handle", ICommandSender.class, String[].class);

      if (method.isAnnotationPresent(OnlyAllowedSenders.class)) {
        if (sender == null) {
          commandResult = Optional.of(new ICommandResult.ErrorIllegalSender(null));
        } else if (Arrays.stream(method.getAnnotation(OnlyAllowedSenders.class).identifiers())
                .noneMatch(sender.getIdentifier()::equals)) {
          commandResult = Optional.of(new ICommandResult.ErrorIllegalSender(sender.getClass()));
        }
      }

      if (commandResult.isEmpty() && method.isAnnotationPresent(PermissionRequired.class)) {
        final String permission = method.getAnnotation(PermissionRequired.class).permission();

        if (sender == null || !sender.hasPermission(permission)) {
          commandResult = Optional.of(new ICommandResult.ErrorPermission(permission));
        }
      }
    } catch (final NoSuchMethodException e) {
      e.printStackTrace();
    }

    if (commandResult.isEmpty()) {
      commandResult = Optional.ofNullable(command.handle(sender, args));
    }

    return commandResult.orElse(null);
  }

  private Optional<ICommand> getCommandMatchingInvokes(final Collection<String> invokes) {
    return this.commands.stream()
            .filter(command -> invokesMatch(command.getInvokes(), invokes.toArray(new String[0])))
            .findFirst();
  }

  /**
   * Splits the message into an array without spaces.
   * Note: The first element contains all spaces in front for invoke matching purposes.
   *
   * @param message The message to be split.
   * @return The split message.
   */
  private List<String> splitMessage(final String message) {
    final List<String> split = Arrays.asList(message.split("(?<!\\\\)" + this.separator));

    if (this.ignoreBlanks) {
      split.removeIf(String::isBlank);
    }

    return split;
  }

  /**
   * Checks, if the given invokes are the same.
   *
   * @param invokes The invokes of the command.
   * @param args    The arguments of the parsed message.
   * @return True, if the invokes are equal, false, if otherwise.
   */
  private boolean invokesMatch(final String[] invokes, final String[] args) {
    if (invokes.length > args.length) {
      return false;
    }

    for (int i = 0; i < invokes.length; i++) {
      if (!invokes[i].equals(args[i])) {
        return false;
      }
    }

    return true;
  }

  /**
   * Gets the syntax of the command consisting of the call symbol, the default invokes, the
   * invokes and the arguments of the {@link ICommand}.
   *
   * @return The syntax of the command.
   */
  public String getSyntax(final ICommand command) {
    final StringBuilder syntax = new StringBuilder(this.callSymbol);
    final String[] invokes = command.getInvokes();
    final String[] arguments = command.getArguments();

    for (int i = 0; i < this.defaultInvokes.size(); i++) {
      syntax.append(this.defaultInvokes.get(i));

      if (i + 1 < this.defaultInvokes.size()) {
        syntax.append(' ');
      }
    }

    for (int i = 0; i < invokes.length; i++) {
      syntax.append(invokes[i]);

      if (i + 1 < invokes.length) {
        syntax.append(' ');
      }
    }

    for (int i = 0; i < arguments.length; i++) {
      syntax.append(arguments[i]);

      if (i + 1 < arguments.length) {
        syntax.append(' ');
      }
    }

    return syntax.toString();
  }

  /**
   * Registers a {@link ICommand}.
   *
   * @param command The command to be added.
   */
  public void registerCommand(final ICommand command) {
    if (this.commands.stream().map(ICommand::getInvokes)
            .anyMatch(invoke -> Arrays.equals(invoke, command.getInvokes()))) {
      throw new UnsupportedOperationException("Duplicate invokes.");
    }

    this.commands.add(command);
    this.commands.sort(Comparator.comparingInt(cmd -> cmd.getInvokes().length));
    Collections.reverse(this.commands);
  }

  /**
   * Gets a {@link List} of {@link ICommand}s related to their invokes.
   *
   * @return A copy of a list containing commands related to their invokes.
   */
  public List<ICommand> getCommands() {
    return List.copyOf(this.commands);
  }

  /**
   * Gets the call symbol.
   *
   * @return The call symbol.
   */
  public String getCallSymbol() {
    return this.callSymbol;
  }

  /**
   * Gets the default invokes.
   *
   * @return The default invokes.
   */
  public List<String> getDefaultInvokes() {
    return this.defaultInvokes;
  }

  /**
   * Gets the separator.
   *
   * @return The separator.
   */
  public String getSeparator() {
    return this.separator;
  }

  /**
   * Checks if blanks should be ignored.
   *
   * @return True, if blanks should be ignored, false, if otherwise.
   */
  public boolean isIgnoringBlanks() {
    return this.ignoreBlanks;
  }
}
