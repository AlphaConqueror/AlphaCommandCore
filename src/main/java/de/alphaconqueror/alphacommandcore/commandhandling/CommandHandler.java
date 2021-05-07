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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CommandHandler {

  private final List<ICommand> commands = new ArrayList<>();
  private final Set<ICommandSender> senders = new HashSet<>();
  private final String callSymbol;
  private String separator = " ";
  private boolean ignoreBlanks = true;

  public CommandHandler(final String callSymbol) {
    this.callSymbol = callSymbol;
  }

  public CommandHandler(final String callSymbol, final String separator) {
    this.callSymbol = callSymbol;
    this.separator = separator;
  }

  public CommandHandler(final String callSymbol, final boolean ignoreBlanks) {
    this.callSymbol = callSymbol;
    this.ignoreBlanks = ignoreBlanks;
  }

  public CommandHandler(final String callSymbol, final String separator,
                        final boolean ignoreBlanks) {
    this.callSymbol = callSymbol;
    this.separator = separator;
    this.ignoreBlanks = ignoreBlanks;
  }

  /**
   * Handles a {@link ICommand}.
   *
   * @param command The command to be handled.
   * @param args    The arguments of the command.
   * @param sender  The {@link ICommandSender} of the command.
   *
   * @return The {@link ICommandResult} of the handled command.
   */
  public ICommandResult handleCommand(final Optional<ICommand> command, final String[] args,
                                      final ICommandSender sender) {
    Optional<ICommandResult> commandResult = Optional.empty();
    Optional<String> permission = Optional.empty();

    if (command.isEmpty()) {
      commandResult = Optional.of(new ICommandResult.ErrorCommandNotFound(args[0]));
    } else {
      try {
        for (final Method method : command.get().getClass().getMethods()) {
          if (ICommand.class.getMethod("handle", ICommandSender.class, String[].class)
                  .equals(command.get().getClass()
                          .getMethod("handle", ICommandSender.class, String[].class))) {
            if (method.isAnnotationPresent(PermissionRequired.class)) {
              permission = Optional.of(method.getAnnotation(PermissionRequired.class).permission());
            }
            break;
          }
        }
      } catch (final NoSuchMethodException ignored) { }
    }

    if (permission.isPresent() && !sender.hasPermission(permission.get())) {
      commandResult = Optional.of(new ICommandResult.ErrorPermission(permission.get()));
    }

    if (commandResult.isEmpty()) {
      commandResult = Optional.of(command.orElseThrow().handle(sender, args));
    }

    AlphaEventCore.callEvent(new CommandCalledEvent(sender, args, commandResult.get()));

    return commandResult.get();
  }

  /**
   * Handles a {@link ICommand} by a given message.
   *
   * @param message The message to be parsed and handled.
   * @param sender  The {@link ICommandSender} of the message.
   *
   * @return The {@link ICommandResult} of the handled command.
   */
  public ICommandResult handle(final String message, final ICommandSender sender) {
    if (message.startsWith(this.callSymbol)) {
      return new ICommandResult.ErrorNoCommand(this.callSymbol);
    }

    final List<String> args = splitMessage(message.substring(this.callSymbol.length()));

    return handleCommand(getCommandMatchingInvokes(args), args.toArray(new String[0]), sender);
  }

  /**
   * Handles a {@link ICommand} by given arguments.
   * Note: Does not check if the call symbol of the command is present.
   *
   * @param args   The arguments to be handled.
   * @param sender The {@link ICommandSender} of the arguments.
   *
   * @return The {@link ICommandResult} of the handled command.
   */
  public ICommandResult handle(final String[] args, final ICommandSender sender) {
    return handleCommand(getCommandMatchingInvokes(Arrays.asList(args)), args, sender);
  }

  private Optional<ICommand> getCommandMatchingInvokes(final List<String> invokes) {
    return this.commands.stream().filter(command -> invokesEqual(command.getInvokes(), invokes))
            .findFirst();
  }

  /**
   * Splits the message into an array without spaces.
   * Note: The first element contains all spaces in front for invoke matching purposes.
   *
   * @param message The message to be split.
   *
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
   *
   * @return True, if the invokes are equal, false, if otherwise.
   */
  private boolean invokesEqual(final String[] invokes, final List<String> args) {
    if (invokes.length > args.size()) {
      return false;
    }

    for (int i = 0; i < invokes.length; i++) {
      if (!invokes[i].equals(args.get(i))) {
        return false;
      }
    }

    return true;
  }

  /**
   * Gets the syntax of the command consisting of the call symbol, the invokes and the arguments.
   *
   * @return The syntax of the command.
   */
  public String getSyntax(final ICommand command) {
    final StringBuilder syntax = new StringBuilder(this.callSymbol);
    final String[] invokes = command.getInvokes();
    final String[] arguments = command.getArguments();

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
    this.commands
            .sort(Comparator.comparingInt(cmd -> cmd.getInvokes().length));
    Collections.reverse(this.commands);
  }

  /**
   * Adds a {@link ICommandSender} to a {@link Set}.
   *
   * @param sender The sender to be added.
   */
  public void addSender(final ICommandSender sender) {
    this.senders.add(sender);
  }

  /**
   * Adds a {@link Collection} of {@link ICommandSender}s to a {@link Set}.
   *
   * @param senders The senders to be added.
   */
  public void addSenders(final Collection<ICommandSender> senders) {
    senders.forEach(sender -> this.addSender(sender));
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
   * Gets a {@link Set} of {@link ICommandSender}s.
   *
   * @return A copy of a set containing command senders.
   */
  public Set<ICommandSender> getSenders() {
    return Set.copyOf(this.senders);
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
   * Gets the separator.
   *
   * @return The separator.
   */
  public String getSeparator() {
    return this.separator;
  }

  /**
   * Sets the separator.
   *
   * @param separator The separator to be set.
   */
  public void setSeparator(final String separator) {
    this.separator = separator;
  }

  /**
   * Checks if blanks should be ignored.
   *
   * @return True, if blanks should be ignored, false, if otherwise.
   */
  public boolean isIgnoringBlanks() {
    return this.ignoreBlanks;
  }

  /**
   * Sets if blanks should be ignored.
   *
   * @param ignoreBlanks True, if blanks should be ignored, false, if otherwise.
   */
  public void setIgnoreBlanks(final boolean ignoreBlanks) {
    this.ignoreBlanks = ignoreBlanks;
  }
}
