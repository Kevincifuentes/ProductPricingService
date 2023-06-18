package com.inditex.hiring.application.cqrs;

public interface CommandHandler<P extends Command> {
    void execute(P command);
}
