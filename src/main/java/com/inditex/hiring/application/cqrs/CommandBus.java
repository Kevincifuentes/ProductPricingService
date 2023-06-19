package com.inditex.hiring.application.cqrs;

public interface CommandBus {
    void execute(Command command);
}
