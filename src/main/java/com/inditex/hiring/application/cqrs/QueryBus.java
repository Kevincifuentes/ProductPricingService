package com.inditex.hiring.application.cqrs;

public interface QueryBus {
    <T> T ask(Query<T> query);
}
