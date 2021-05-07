/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public interface ICommandResult {

  /**
   * Says if the {@link ICommand} is executable when returning the result.
   *
   * @return true, if the command is executable, false, if otherwise.
   */
  boolean isExecutable();

  /**
   * The command result representing a {@link ICommand} that can be executed.
   */
  final class Okay implements ICommandResult {

    @Override
    public boolean isExecutable() {
      return true;
    }
  }

  /**
   * The command result representing a {@link ICommand} that can not be executed due to a
   * permission error.
   */
  final class ErrorPermission implements ICommandResult {

    private final String permissionIdentifier;

    public ErrorPermission(final String permissionIdentifier) {
      this.permissionIdentifier = permissionIdentifier;
    }

    @Override
    public boolean isExecutable() {
      return false;
    }

    /**
     * Gets the permission identifier of the missing permission.
     *
     * @return The permission identifier of the missing permission.
     */
    public String getPermissionIdentifier() {
      return this.permissionIdentifier;
    }
  }

  /**
   * The command result representing a {@link ICommand} that can not be executed
   * due to the input message not starting a correct call symbol.
   */
  final class ErrorNoCommand implements ICommandResult {

    private final String callSymbol;

    public ErrorNoCommand(final String callSymbol) {
      this.callSymbol = callSymbol;
    }

    @Override
    public boolean isExecutable() {
      return false;
    }

    /**
     * Gets the call symbol of the input message.
     *
     * @return The call symbol of the input message.
     */
    public String getCallSymbol() {
      return this.callSymbol;
    }
  }

  /**
   * The command result representing a {@link ICommand} that can not be executed due to not
   * finding it.
   */
  final class ErrorCommandNotFound implements ICommandResult {

    private final String invoke;

    public ErrorCommandNotFound(final String invoke) {
      this.invoke = invoke;
    }

    @Override
    public boolean isExecutable() {
      return false;
    }

    /**
     * Gets the invoke of the missing {@link ICommand}.
     *
     * @return The invoke of the missing command.
     */
    public String getInvoke() {
      return this.invoke;
    }
  }
}
