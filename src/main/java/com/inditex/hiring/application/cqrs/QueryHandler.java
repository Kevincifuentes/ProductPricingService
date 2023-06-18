package com.inditex.hiring.application.cqrs;

public interface QueryHandler<P extends Query, R> {
  R ask(P query);
}
