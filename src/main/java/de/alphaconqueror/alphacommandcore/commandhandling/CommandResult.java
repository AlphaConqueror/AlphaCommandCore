/*
 * @author: AlphaConqueror
 * Copyright (c) 2020
 * All rights reserved.
 */

package de.alphaconqueror.alphacommandcore.commandhandling;

public abstract class CommandResult {

    /**
     * Says if the command is executable when returning the result.
     *
     * @return true, if the command is executable, false, if otherwise.
     */
    public abstract boolean isExecutable();

    /**
     * The static command result representing a command that can be executed.
     */
    public static final class OK extends CommandResult {

        @Override
        public boolean isExecutable() {
            return true;
        }
    }

    /**
     * The static command result representing a command that can not be executed due to a permission error.
     */
    public static final class ERROR_PERMISSION extends CommandResult {

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
     * The static command result representing an {@link ICommand} that can not be executed due to not finding it.
     */
    public static final class ERROR_COMMAND_NOT_FOUND extends CommandResult {

        private final String invoke;

        public ERROR_COMMAND_NOT_FOUND(String invoke) {
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
            return invoke;
        }
    }
}
