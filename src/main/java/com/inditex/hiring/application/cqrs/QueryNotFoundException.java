package com.inditex.hiring.application.cqrs;

import static java.lang.String.format;

public final class QueryNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_QUERY_TEMPLATE = "No handler found for query %s";

    public QueryNotFoundException(final String userQuery) {
        super(format(NOT_FOUND_QUERY_TEMPLATE, userQuery));
    }
}
