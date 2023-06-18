package com.inditex.hiring.application;

public interface CommandHandler<T extends Command> {
    void execute(T command);
}
