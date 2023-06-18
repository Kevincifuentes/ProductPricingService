package com.inditex.hiring.application;

import static java.lang.String.format;

public final class CommandNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_COMMAND_TEMPLATE = "No handler found for command %s";

    public CommandNotFoundException(final String usedCommand) {
        super(format(NOT_FOUND_COMMAND_TEMPLATE, usedCommand));
    }
}
