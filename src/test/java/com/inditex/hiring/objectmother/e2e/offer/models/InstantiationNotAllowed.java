package com.inditex.hiring.objectmother.e2e.offer.models;

import static java.lang.String.format;

public final class InstantiationNotAllowed extends Exception {
    public InstantiationNotAllowed(final String className) {
        super(format("Instantiation on class %s not allowed", className));
    }
}
