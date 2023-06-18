package com.inditex.hiring.application;

public interface CommandHandler<P extends Command> {
    void execute(P command);
}
