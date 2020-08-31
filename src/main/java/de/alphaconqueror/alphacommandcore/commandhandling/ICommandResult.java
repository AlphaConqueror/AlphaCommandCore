/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public interface ICommandResult {

    /**
     * Says if the {@link Command} is executable when returning the result.
     *
     * @return true, if the command is executable, false, if otherwise.
     */
    boolean isExecutable();

    /**
     * The command result representing a {@link Command} that can be executed.
     */
    final class OK implements ICommandResult {

        @Override
        public boolean isExecutable() {
            return true;
        }
    }

    /**
     * The command result representing a {@link Command} that can not be executed due to a permission error.
     */
    final class ERROR_PERMISSION implements ICommandResult {

        private final String permissionIdentifier;

        public ERROR_PERMISSION(String permissionIdentifier) {
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
            return permissionIdentifier;
        }
    }

    /**
     * The command result representing a {@link Command} that can not be executed
     * due to the input message not starting a correct call symbol.
     */
    final class ERROR_NO_COMMAND implements ICommandResult {

        private final char callSymbol;

        public ERROR_NO_COMMAND(char callSymbol) {
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
        public char getCallSymbol() {
            return callSymbol;
        }
    }

    /**
     * The command result representing a {@link Command} that can not be executed due to not finding it.
     */
    final class ERROR_COMMAND_NOT_FOUND implements ICommandResult {

        private final String invoke;

        public ERROR_COMMAND_NOT_FOUND(String invoke) {
            this.invoke = invoke;
        }

        @Override
        public boolean isExecutable() {
            return false;
        }

        /**
         * Gets the invoke of the missing {@link Command}.
         *
         * @return The invoke of the missing command.
         */
        public String getInvoke() {
            return invoke;
        }
    }
}
