package com.inditex.hiring.application.cqrs;

import static java.lang.String.format;

public final class CommandClassNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_COMMAND_TEMPLATE = "No Command class type found for %s";

    public CommandClassNotFoundException(final String typeName) {
        super(format(NOT_FOUND_COMMAND_TEMPLATE, typeName));
    }
}
