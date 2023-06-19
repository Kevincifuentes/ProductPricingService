package com.inditex.hiring.application.cqrs;

import static java.lang.String.format;

public final class QueryClassNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_QUERY_TEMPLATE = "No Query class type found for %s";

    public QueryClassNotFoundException(final String typeName) {
        super(format(NOT_FOUND_QUERY_TEMPLATE, typeName));
    }
}
